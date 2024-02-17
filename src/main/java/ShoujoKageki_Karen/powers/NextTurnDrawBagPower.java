package ShoujoKageki_Karen.powers;


import ShoujoKageki_Karen.actions.bag.TakeCardFromBagAction;
import ShoujoKageki.base.BasePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class NextTurnDrawBagPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(NextTurnDrawBagPower.class.getName());

    private static final String RAW_ID = NextTurnDrawBagPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public NextTurnDrawBagPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        flash();
        addToBot(new TakeCardFromBagAction(amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
