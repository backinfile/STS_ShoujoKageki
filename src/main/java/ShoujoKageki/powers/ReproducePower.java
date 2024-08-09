package ShoujoKageki.powers;


import ShoujoKagekiCore.base.BasePower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class ReproducePower extends BasePower {
    public static final Logger logger = LogManager.getLogger(ReproducePower.class.getName());

    private static final String RAW_ID = ReproducePower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public ReproducePower() {
        super(POWER_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, -1);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }
}
