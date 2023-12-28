package ShoujoKageki.powers;


import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class TragedyPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(TragedyPower.class.getName());

    private static final String RAW_ID = TragedyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public TragedyPower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, 1);
    }

    @Override
    public void onVictory() {
        super.onVictory();

        for (int i = 0; i < amount; i++) {
            AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
            AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Decay(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
            flash();
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
