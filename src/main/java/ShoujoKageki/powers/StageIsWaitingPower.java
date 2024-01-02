package ShoujoKageki.powers;


import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class StageIsWaitingPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(StageIsWaitingPower.class.getName());

    private static final String RAW_ID = StageIsWaitingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public StageIsWaitingPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
