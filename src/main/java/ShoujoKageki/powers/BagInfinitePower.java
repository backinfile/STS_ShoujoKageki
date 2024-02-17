package ShoujoKageki.powers;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class BagInfinitePower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BagInfinitePower.class.getName());

    private static final String RAW_ID = BagInfinitePower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public BagInfinitePower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, -1);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
