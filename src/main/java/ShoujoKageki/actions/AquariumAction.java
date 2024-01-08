package ShoujoKageki.actions;

import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.powers.AquariumPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
