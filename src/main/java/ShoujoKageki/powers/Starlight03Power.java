package ShoujoKageki.powers;


import ShoujoKageki.reward.ShineCardReward;
import ShoujoKageki.variables.DisposableVariable;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ShoujoKageki.ModInfo.makeID;

public class Starlight03Power extends BasePower {
    public static final Logger logger = LogManager.getLogger(Starlight03Power.class.getName());

    private static final String RAW_ID = Starlight03Power.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public Starlight03Power() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, 1);
        updateDescription();
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
        updateDescription();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    private void addReward() {
        RewardItem rewardItem = new ShineCardReward();
        if (rewardItem.cards.isEmpty()) return;
        AbstractDungeon.getCurrRoom().addCardReward(rewardItem);
    }

    private static AbstractCard.CardRarity getRarity() {
        int random = AbstractDungeon.cardRng.random(99);
        if (random < 3) return AbstractCard.CardRarity.RARE;
        if (random < 40) return AbstractCard.CardRarity.UNCOMMON;
        return AbstractCard.CardRarity.COMMON;
    }
}
