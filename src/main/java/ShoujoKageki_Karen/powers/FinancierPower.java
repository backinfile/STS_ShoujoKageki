package ShoujoKageki_Karen.powers;


import ShoujoKageki.base.BasePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class FinancierPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(FinancierPower.class.getName());

    private static final String RAW_ID = FinancierPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    private int turnCount = 1;

    public FinancierPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        turnCount++;
        if (turnCount % 2 == 0) {
            flash();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new ReserveStrengthPower(amount)));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
