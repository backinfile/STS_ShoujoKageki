package ShoujoKageki.actions;

import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.actions.bag.MoveCardToBagAction;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MoveAllShineCardsIntoBagAction extends AbstractGameAction {
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> allShineCards = StarAction.getAllShineCardsWithoutBag();
        if (allShineCards.isEmpty()) {
            isDone = true;
            return;
        }
        boolean refreshHand = false;
        boolean applyBagPower = false;
        for (AbstractCard card : allShineCards) {
            if (p.hand.group.remove(card)) refreshHand = true;
            p.discardPile.group.remove(card);
            p.drawPile.group.remove(card);
            if (BagField.getBag().group.remove(card)) applyBagPower = true;
            p.limbo.addToTop(card);
        }
        if (refreshHand) p.hand.refreshHandLayout();
        if (applyBagPower) addToBot(new ApplyBagPowerAction());
        addToBot(new MoveCardToBagAction(allShineCards, true));
        isDone = true;
    }
}
