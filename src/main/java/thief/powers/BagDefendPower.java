package thief.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static thief.ModInfo.makeID;

public class BagDefendPower extends BasePower {
    public static final String POWER_ID = makeID(BagDefendPower.class.getSimpleName());
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    private static final String tex84 = "placeholder_power84.png";
    private static final String tex32 = "placeholder_power32.png";

    public BagDefendPower(int amount) {
        super(POWER_ID, POWER_ID, AbstractPower.PowerType.BUFF, tex84, tex32,
                AbstractDungeon.player, AbstractDungeon.player, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new GainBlockAction(AbstractDungeon.player, amount));
    }
}
