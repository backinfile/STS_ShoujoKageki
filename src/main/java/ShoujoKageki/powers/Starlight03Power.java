package ShoujoKageki.powers;


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
        RewardItem cardReward = new RewardItem();
        ArrayList<AbstractCard> cardsToObtain = new ArrayList<>();
        for (int i = 0; i < cardReward.cards.size(); i++) { // replace card to shine card
            for (int tryTimes = 0; tryTimes < 5; tryTimes++) {
                AbstractCard.CardRarity rarity = getRarity();
                List<AbstractCard> list;
                if (rarity.equals(AbstractCard.CardRarity.COMMON)) {
                    list = AbstractDungeon.srcCommonCardPool.group;
                } else if (rarity.equals(AbstractCard.CardRarity.UNCOMMON)) {
                    list = AbstractDungeon.srcUncommonCardPool.group;
                } else {
                    list = AbstractDungeon.srcRareCardPool.group;
                }
                list = list.stream().filter(DisposableVariable::isDisposableCard).collect(Collectors.toList());
                if (list.isEmpty()) continue;
                int rnd = AbstractDungeon.cardRandomRng.random(list.size() - 1);
                AbstractCard targetCard = list.get(rnd);
                if (cardsToObtain.contains(targetCard)) continue; // distinct
                cardsToObtain.add(targetCard.makeCopy());
                break;
            }
        }

        cardReward.cards = cardsToObtain;

        float cardUpgradedChance = ReflectionHacks.getPrivateStatic(AbstractDungeon.class, "cardUpgradedChance");
        for (AbstractCard card : cardsToObtain) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(card);
            }
            if (card.canUpgrade()) {
                if (card.rarity != AbstractCard.CardRarity.RARE && AbstractDungeon.cardRng.randomBoolean(cardUpgradedChance)) {
                    card.upgrade();
                }
            }
        }
        AbstractDungeon.getCurrRoom().addCardReward(cardReward);
    }

    private static AbstractCard.CardRarity getRarity() {
        int random = AbstractDungeon.cardRng.random(99);
        if (random < 3) return AbstractCard.CardRarity.RARE;
        if (random < 40) return AbstractCard.CardRarity.UNCOMMON;
        return AbstractCard.CardRarity.COMMON;
    }
}
