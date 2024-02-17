package ShoujoKageki.actions.bag;

import ShoujoKageki.powers.BagPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyBagPowerAction extends AbstractGameAction {
    public ApplyBagPowerAction(int amount) {
        this.amount = amount;
    }

    public ApplyBagPowerAction() {
        this(1);
    }

    @Override
    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;

        AbstractPower power = p.getPower(BagPower.POWER_ID);
        if (power == null) {
            addToBot(new ApplyPowerAction(p, p, new BagPower(amount)));
        } else {
            power.onSpecificTrigger();
        }
    }
}
