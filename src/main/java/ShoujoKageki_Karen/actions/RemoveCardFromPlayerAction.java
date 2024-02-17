package ShoujoKageki_Karen.actions;

import ShoujoKageki_Karen.actions.bag.ApplyBagPowerAction;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveCardFromPlayerAction extends AbstractGameAction {

    private final AbstractCard card;

    public RemoveCardFromPlayerAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        isDone = true;

        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.group.remove(card)) {
            p.hand.refreshHandLayout();
        }
        p.discardPile.group.remove(card);
        p.drawPile.group.remove(card);
        if (BagField.getBag().group.remove(card)) {
            addToBot(new ApplyBagPowerAction());
        }
    }
}
