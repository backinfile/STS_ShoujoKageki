package ShoujoKageki.actions.bag;

import ShoujoKageki.cards.patches.field.BagField;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ReplaceHandAndBagAction extends AbstractGameAction {

    public ReplaceHandAndBagAction() {
    }

    @Override
    public void update() {
        replaceAllCardInHand();
        isDone = true;
    }

    private void replaceAllCardInHand() {
        AbstractPlayer player = AbstractDungeon.player;
        if (BagField.isChangeToDrawPile()) {
            ArrayList<AbstractCard> cardsInBag = new ArrayList<>(player.drawPile.group);
            player.drawPile.group.clear();

            PutHandCardIntoBagAction.bagCards(player.hand.group);


            if (BagField.isInfinite()) {
                addToBot(new TakeRndTmpCardFromBagAction(BaseMod.MAX_HAND_SIZE));
                return;
            }
            addToBot(new TakeSpecCardFromBagAction(cardsInBag, true));
            return;
        }


        CardGroup bag = BagField.bag.get(player);
        int beforeAmount = bag.size();

        ArrayList<AbstractCard> bagCards = new ArrayList<>(bag.group);
        bag.clear();
        addToBot(new ApplyBagPowerAction(-beforeAmount));
        if (beforeAmount > 0 && player.hand.size() > 0) {
            addToTop(new CheckBagEmptyAction());
        }
        PutHandCardIntoBagAction.bagCards(player.hand.group);

        if (BagField.isInfinite()) {
            addToBot(new TakeRndTmpCardFromBagAction(BaseMod.MAX_HAND_SIZE));
            return;
        }
//        addToBot(new PutHandCardIntoBagAction(player, true, false));
        addToBot(new TakeSpecCardFromBagAction(bagCards, true));
    }
}
