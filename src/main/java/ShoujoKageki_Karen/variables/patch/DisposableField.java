package ShoujoKageki_Karen.variables.patch;

import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.effects.PurgeCardInBattleEffect;
import ShoujoKageki_Karen.patches.TokenCardField;
import ShoujoKageki_Karen.patches.showDisposeInHistory.MetricDataPatch;
import ShoujoKageki.util.ActionUtils;
import ShoujoKageki.util.Utils2;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractCard.class,
        method = "<class>"
)
public class DisposableField {
    private static final int RECORD_MAX = 200;

    public static SpireField<Integer> disposable = new SpireField<>(() -> 0);// use DisposableVariable.setValue instead
    public static SpireField<Integer> baseDisposable = new SpireField<>(() -> 0); // use DisposableVariable.setBaseValue instead
    public static SpireField<Boolean> disposableModified = new SpireField<>(() -> false);
    public static SpireField<Boolean> forceDispose = new SpireField<>(() -> false);

    public static CardGroup getDisposedPile() {
        CardGroup disposedPile = DisposableField2.disposedPile.get(AbstractDungeon.player);
        if (disposedPile == null) {
            disposedPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            DisposableField2.disposedPile.set(AbstractDungeon.player, disposedPile);
        }
        return disposedPile;
    }

    public static void disposeCard(AbstractCard card) {
        disposeCard(card, card.current_x, card.current_y);
    }

    public static void disposeCard(AbstractCard card, float x, float y) {
        AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(card);
        if (cardInDeck != null) {
            AbstractDungeon.player.masterDeck.removeCard(cardInDeck);
        }

        if (card.type != AbstractCard.CardType.POWER) {
            float centerX = (float) Settings.WIDTH / 2.0F;
            float centerY = (float) Settings.HEIGHT / 2.0F;
            card.target_x = centerX;
            card.target_y = centerY;
            card.current_x = x;
            card.current_y = y;
            ActionUtils.resetBeforeMoving(card);
            card.lighten(false);
            card.targetAngle = 0.0F;
            AbstractDungeon.effectList.add(new PurgeCardInBattleEffect(card, card.current_x, card.current_y));
        }

        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnDisposed();
        }

        DisposableFieldCounterSavePatch.addShineCardDispose(card);
        DisposableFieldRecordPatch.addDisposedCard(card);

        {
            AbstractCard copyCard = Utils2.makeCardCopyOnlyWithUpgrade(card);
            TokenCardField.isToken.set(copyCard, false);
            CardGroup disposedPile = getDisposedPile();
            if (disposedPile.size() < RECORD_MAX) {
                disposedPile.group.add(copyCard);
            }
        }

        MetricDataPatch.addDisposedCard(card);
    }
}
