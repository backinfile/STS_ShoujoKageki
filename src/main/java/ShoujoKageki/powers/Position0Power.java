package ShoujoKageki.powers;


import ShoujoKageki.powers.patch.StrengthPowerPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class Position0Power extends BasePower {
    public static final Logger logger = LogManager.getLogger(Position0Power.class.getName());

    private static final String RAW_ID = Position0Power.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public Position0Power(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (target == this.owner) {
            if (power instanceof StrengthPower) {
                if (StrengthPowerPatch.StrengthPowerField.triggerEntrance.get(power)) {
                    if (power.amount > 0) {
                        StrengthPower powerToApply = new StrengthPower(this.owner, amount);
                        StrengthPowerPatch.StrengthPowerField.triggerEntrance.set(powerToApply, false);
                        addToBot(new ApplyPowerAction(this.owner, this.owner, powerToApply));
                        flash();
                    }
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
