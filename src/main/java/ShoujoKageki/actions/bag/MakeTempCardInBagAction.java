package ShoujoKageki.actions.bag;

import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import ShoujoKageki.effects.ShowAndHoldCardThenEffect;

import java.util.ArrayList;


// TODO change to cardList
public class MakeTempCardInBagAction extends AbstractGameAction {
    private final AbstractCard cardToMake;
    private final boolean autoPosition;
    private final boolean fast;

    public MakeTempCardInBagAction(AbstractCard card, int amount, boolean autoPosition, boolean fast) {
        this.fast = fast;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : Settings.ACTION_DUR_MED;
        this.duration = this.startDuration;
        this.cardToMake = card;
        this.autoPosition = autoPosition;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPlayer player = AbstractDungeon.player;

            if (BagField.isChangeToDrawPile()) {
                addToTop(new MakeTempCardInDrawPileAction(cardToMake, amount, false, autoPosition, true));
                isDone = true;
                return;
            }

            ArrayList<AbstractCard> cards = new ArrayList<>();

            for (int i = 0; i < amount; i++) {
                AbstractCard card = this.cardToMake.makeStatEquivalentCopy();
                if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && player.hasPower(MasterRealityPower.POWER_ID)) {
                    card.upgrade();
                }
                CardCrawlGame.sound.play("CARD_OBTAIN");
                AbstractDungeon.effectList.add(new ShowAndHoldCardThenEffect(card, true, autoPosition, null));
                cards.add(card);
            }
            addToTop(new MoveCardToBagAction(cards));
            addToTop(new WaitAction(ShowAndHoldCardThenEffect.DURATION));

            if (fast) isDone = true;
        }
        tickDuration();
    }
}
