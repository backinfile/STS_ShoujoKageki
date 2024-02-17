package ShoujoKageki_Karen.powers;


import ShoujoKageki_Karen.actions.AquariumAction;
import ShoujoKageki.base.BasePower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class AquariumPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(AquariumPower.class.getName());

    private static final String RAW_ID = AquariumPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public AquariumPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
//        addToBot(new TakeCardFromBagAction(amount));
        addToBot(new AquariumAction(amount));
        flash();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];
        }
    }
}
