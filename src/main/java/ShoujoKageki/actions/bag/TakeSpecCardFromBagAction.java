package ShoujoKageki.actions.bag;

import ShoujoKageki.karen.cards.patches.BagFieldPatch;
import ShoujoKageki.karen.cards.patches.field.BagField;
import ShoujoKageki.effects.ShowBagCardAndAddToDiscardEffect;
import ShoujoKageki.effects.ShowBagCardToHandEffect;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class TakeSpecCardFromBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToTake = new ArrayList<>();
    private final boolean discardOverflowedCard;

    public TakeSpecCardFromBagAction(ArrayList<AbstractCard> cardsToTake, boolean discardOverflowedCard) {
        this.discardOverflowedCard = discardOverflowedCard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardsToTake.addAll(cardsToTake);
    }

    public TakeSpecCardFromBagAction(AbstractCard cardToTake, boolean discardOverflowedCard) {
        this.discardOverflowedCard = discardOverflowedCard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardsToTake.add(cardToTake);
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (cardsToTake.isEmpty()) {
                isDone = true;
                return;
            }

            CardGroup bag = BagField.bag.get(player);
            int handSize = player.hand.size();
            int toTake = cardsToTake.size();
            int takeCnt = 0;
            for (int i = 0; i < toTake; i++) {
                AbstractCard curCard = cardsToTake.get(i);


                if (handSize + i >= BaseMod.MAX_HAND_SIZE) {
                    if (discardOverflowedCard) {
                        BagFieldPatch.triggerOnTakeFromBag(curCard);
                        AbstractDungeon.effectList.add(new ShowBagCardAndAddToDiscardEffect(curCard));
                    } else {
                        break;
                    }
                } else {
                    BagFieldPatch.triggerOnTakeFromBagToHand(curCard);
                    AbstractDungeon.effectList.add(new ShowBagCardToHandEffect(curCard));
                }
                bag.removeCard(curCard);
                takeCnt++;
            }
            addToTop(new CheckBagEmptyAction());
            addToTop(new ApplyBagPowerAction());
//            addToTop(new ReducePowerAction(player, player, BagPower.POWER_ID, takeCnt));
            isDone = true;
            return;
        }
        tickDuration();
    }
}
