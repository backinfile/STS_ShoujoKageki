package thief.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thief.actions.TakeCardFromBagAction;
import thief.cards.bag.BagEnergy;
import thief.cards.patch.BagField;
import thief.util.Utils2;

import static thief.ModInfo.makeID;

public class BagPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BagPower.class.getName());

    public static final String RAW_ID = BagPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public BagPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF,
                AbstractDungeon.player, AbstractDungeon.player, amount);
        updateDescription();
    }


    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (owner instanceof AbstractPlayer) {
            CardGroup bag = BagField.bag.get(owner);
            if (bag.isEmpty()) {
                description = DESCRIPTIONS[0];
            } else {
                description = Utils2.getCardNames(bag, DESCRIPTIONS[1]);
            }
        } else {
            description = DESCRIPTIONS[0];
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        CardGroup bag = BagField.bag.get(AbstractDungeon.player);
        for (AbstractCard card : bag.group) {
            if (card instanceof BagEnergy) {
                card.use(AbstractDungeon.player, null);
                this.flashWithoutSound();
            }
        }

        this.flashWithoutSound();
        addToBot(new TakeCardFromBagAction());
    }
}
