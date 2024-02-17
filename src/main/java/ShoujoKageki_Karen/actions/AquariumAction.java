package ShoujoKageki_Karen.actions;

import ShoujoKageki_Karen.actions.bag.PutHandCardIntoBagAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class AquariumAction extends AbstractGameAction {
    public AquariumAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        addToTop(new PutHandCardIntoBagAction(amount));
        addToTop(new DrawCardAction(amount));
        isDone = true;
    }
}
