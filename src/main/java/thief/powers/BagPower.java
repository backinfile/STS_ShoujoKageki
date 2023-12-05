package thief.powers;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thief.Log;
import thief.util.Utils2;

import java.util.ArrayList;
import java.util.List;

import static thief.ModInfo.makeID;

public class BagPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BagPower.class.getName());

    public static final String POWER_ID = makeID(BagPower.class.getSimpleName());
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    private static final String tex84 = "placeholder_power84.png";
    private static final String tex32 = "placeholder_power32.png";

    public final CardGroup bag = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public BagPower(List<AbstractCard> cards) {
        super(POWER_ID, POWER_ID, PowerType.BUFF, tex84, tex32,
                AbstractDungeon.player, AbstractDungeon.player, cards.size());
        this.bag.group.addAll(cards);
        updateDescription();
    }

    public BagPower(int num) {
        super(POWER_ID, POWER_ID, PowerType.BUFF, tex84, tex32,
                AbstractDungeon.player, AbstractDungeon.player, num);
    }


    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void updateDescription() {
        if (bag == null) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[0] + Utils2.getCardNames(bag, DESCRIPTIONS[1]);
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        addToBot(new GainBlockAction(owner, bag.size()));
    }


    public void addBagCards(ArrayList<AbstractCard> cardGroup) {
        this.bag.group.addAll(cardGroup);
        updateDescription();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
    }
}
