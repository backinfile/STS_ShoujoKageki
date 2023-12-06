package thief.powers;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thief.Log;
import thief.actions.TakeCardFromBagAction;
import thief.cards.tool.patch.BagField;
import thief.character.BasePlayer;
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

    public BagPower(int amount) {
        super(POWER_ID, POWER_ID, PowerType.BUFF, tex84, tex32,
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

        this.flashWithoutSound();
        addToBot(new TakeCardFromBagAction());
    }
}
