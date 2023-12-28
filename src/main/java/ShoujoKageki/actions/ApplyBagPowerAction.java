package ShoujoKageki.actions;

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
        this(0);
    }

    @Override
    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasPower(VoidPower.POWER_ID)) { // 空虚能力把不要背包了
            if (p.hasPower(BagPower.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(p, p, BagPower.POWER_ID));
            }
            if (p.hasPower(BagInfinitePower.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(p, p, BagInfinitePower.POWER_ID));
            }
            return;
        }

        if (BagField.isInfinite()) { // 如果没有空虚能力，但无限了，则要有一个无限的BagPower
            if (p.hasPower(BagPower.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(p, p, BagPower.POWER_ID));
            }
            if (!p.hasPower(BagInfinitePower.POWER_ID)) {
                addToBot(new ApplyPowerAction(p, p, new BagInfinitePower()));
            }
            return;
        }

        if (amount == 0) {
            return;
        }
        if (amount > 0) {
            addToBot(new ApplyPowerAction(p, p, new BagPower(amount)));
        } else {
            addToBot(new ReducePowerAction(p, p, BagPower.POWER_ID, amount));
        }

    }
}
