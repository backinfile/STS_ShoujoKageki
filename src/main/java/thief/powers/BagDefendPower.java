package thief.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thief.cards.patch.BagField;

import static thief.ModInfo.makeID;

public class BagDefendPower extends BasePower {
    public static final String RAW_ID = BagDefendPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    private static final String tex84 = "placeholder_power84.png";
    private static final String tex32 = "placeholder_power32.png";

    public BagDefendPower(int amount) {
        super(POWER_ID, RAW_ID, AbstractPower.PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
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
        if (isPlayer) {
            addToBot(new GainBlockAction(AbstractDungeon.player, amount * BagField.bag.get(AbstractDungeon.player).size()));
        }

    }
}
