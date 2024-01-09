package ShoujoKageki.powers;


import ShoujoKageki.reward.ShineCardReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class ShineRewardPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(ShineRewardPower.class.getName());

    private static final String RAW_ID = ShineRewardPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public ShineRewardPower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, 1);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        ShineCardReward.addShineCardRewardToRoom();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        ShineCardReward.addShineCardRewardToRoom();
        updateDescription();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        flash();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    private static AbstractCard.CardRarity getRarity() {
        int random = AbstractDungeon.cardRng.random(99);
        if (random < 3) return AbstractCard.CardRarity.RARE;
        if (random < 40) return AbstractCard.CardRarity.UNCOMMON;
        return AbstractCard.CardRarity.COMMON;
    }
}
