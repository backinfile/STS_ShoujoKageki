package thief.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import thief.actions.FlashAllEnemySpecPowerAction;
import thief.cards.patch.BagField;

import static thief.ModInfo.makeID;

public class ReduceStrengthLimitPower extends BasePower {
    private static final String RAW_ID = ReduceStrengthLimitPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public ReduceStrengthLimitPower(int amount) {
        super(POWER_ID, RAW_ID, AbstractPower.PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    // 为所有镣铐buff更新一下描述
    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToBot(new FlashAllEnemySpecPowerAction(GainStrengthPower.POWER_ID));
    }

    @Override
    public void stackPower(int stackAmount) {
//        super.stackPower(stackAmount);
    }

    @Override
    public void reducePower(int reduceAmount) {
//        super.reducePower(reduceAmount);
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}