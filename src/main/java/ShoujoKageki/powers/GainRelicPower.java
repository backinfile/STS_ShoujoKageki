package ShoujoKageki.powers;


import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class GainRelicPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(GainRelicPower.class.getName());

    private static final String RAW_ID = GainRelicPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public GainRelicPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void onVictory() {
        super.onVictory();

        for (int i = 0; i < amount; i++) {
            AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
            AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
            flash();
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
