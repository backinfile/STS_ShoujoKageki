package ShoujoKageki.actions.bag;

import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.powers.BagInfinitePower;
import ShoujoKageki.powers.BagPower;
import ShoujoKageki.powers.VoidPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
            addToBot(new ApplyBagPowerAction());
        } else {
            power.onSpecificTrigger();
        }
        fixPowerPosition();
    }

    private void fixPowerPosition() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractPlayer p = AbstractDungeon.player;
                AbstractPower power = p.getPower(BagPower.POWER_ID);
                if (power != null) {
                    int index = p.powers.indexOf(power);
                    if (index > 0) {
                        p.powers.remove(power);
                        p.powers.add(0, power);
                    }
                }
            }
        });
    }
}
