package ShoujoKageki.variables.patch;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.PurgeCardInBattleEffect;
import ShoujoKageki.util.ActionUtils;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractCard.class,
        method = "<class>"
)
public class DisposableField {
    public static SpireField<Integer> disposable = new SpireField<>(() -> 0);// use DisposableVariable.setValue instead
    public static SpireField<Integer> baseDisposable = new SpireField<>(() -> 0); // use DisposableVariable.setBaseValue instead
    public static SpireField<Boolean> forceDispose = new SpireField<>(() -> false);


    public static void disposeCard(AbstractCard card) {
        disposeCard(card, card.current_x, card.current_y);
        AbstractPlayer p = AbstractDungeon.player;
        Integer value = DisposableFieldCounterSavePatch.Field.counter.get(p);
        DisposableFieldCounterSavePatch.Field.counter.set(p, value + 1);
    }

    public static void disposeCard(AbstractCard card, float x, float y) {
        AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(card);
        if (cardInDeck != null) {
            AbstractDungeon.player.masterDeck.removeCard(cardInDeck);
        }

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


        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnDisposed();
        }
    }
}
