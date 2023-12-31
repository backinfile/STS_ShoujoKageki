package ShoujoKageki.powers;


import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class PassionPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(PassionPower.class.getName());

    private static final String RAW_ID = PassionPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public PassionPower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, 1);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addReward();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        addReward();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        flash();
    }

    private static void addReward() {
        AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
        AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
        AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
