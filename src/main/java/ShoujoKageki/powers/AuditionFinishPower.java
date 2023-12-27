package ShoujoKageki.powers;


import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.util.Utils2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class AuditionFinishPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(AuditionFinishPower.class.getName());

    private static final String RAW_ID = AuditionFinishPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    private static int idCnt = 0;

    public AuditionFinishPower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, -1);
        this.ID += idCnt++;
    }

    @Override
    public void onVictory() {
        super.onVictory();

        AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
        AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
        AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Decay(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
    }
}
