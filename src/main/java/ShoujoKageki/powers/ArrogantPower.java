package ShoujoKageki.powers;


import ShoujoKagekiCore.base.BasePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class ArrogantPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(ArrogantPower.class.getName());

    private static final String RAW_ID = ArrogantPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    private static int idCnt = 0;

    public ArrogantPower() {
        super(POWER_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, -1);
        this.ID += idCnt++;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new LoseHPAction(p, p, calcLoseHp(p)));

        AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
        AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
        AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
        flash();

        if (this.amount < 0) {
            this.amount = 1;
        } else {
            this.amount++;
        }
    }

    private static int calcLoseHp(AbstractPlayer p) {
        return (int) Math.floor(p.maxHealth * 0.25);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }
}
