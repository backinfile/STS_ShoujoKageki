package ShoujoKageki.powers;


import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

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
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
