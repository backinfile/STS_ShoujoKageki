package ShoujoKageki.powers;


import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.modifier.BurnModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.util.Utils2;

import static ShoujoKageki.ModInfo.makeID;

public class BagPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BagPower.class.getName());

    private static final String RAW_ID = BagPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public BagPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
        checkBagPower();
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        checkBagPower();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkBagPower();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if (!(owner instanceof AbstractPlayer)) return;

        boolean infinite = BagField.isInfinite(false);
        boolean changeToDrawPile = BagField.isChangeToDrawPile(false);
        boolean showCardsInBag = BagField.showCardsInBag();

        if (showCardsInBag) {
            description += DESCRIPTIONS[1];
        }
        if (changeToDrawPile) {
            description += DESCRIPTIONS[3];
        }
        if (infinite && BagField.isUpgrade()) {
            description += DESCRIPTIONS[9];
        } else if (infinite) {
            description += DESCRIPTIONS[2];
        } else if (BagField.isUpgrade()) {
            description += DESCRIPTIONS[4];
        }

        if (BagField.isBurn()) {
            description += DESCRIPTIONS[5];
        }

        if (!showCardsInBag) {
            return;
        }

        CardGroup bag = BagField.bag.get(owner);
        if (bag.isEmpty()) {
            description += DESCRIPTIONS[6];
        } else {
            description += DESCRIPTIONS[7] + Utils2.getCardNames(bag, DESCRIPTIONS[8], false);
        }
    }

    public void checkBagPower() {
        int oldAmount = this.amount;
        int newAmount = BagField.showCardsInBag() ? BagField.getBag().size() : 0;
        if (newAmount == 0) newAmount = -1;

        if (oldAmount != newAmount) {
            this.amount = newAmount;
            flash();
        }


        updateDescription();
    }


    //================ bag =========================
    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        CardGroup bag = BagField.bag.get(AbstractDungeon.player);
        for (AbstractCard card : bag.group) {
            if (card instanceof BaseCard) {
                ((BaseCard) card).triggerOnTurnStartInBag();
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);

        if (isPlayer) {
            for (AbstractCard card : BagField.bag.get(AbstractDungeon.player).group) {
                if (card instanceof BaseCard) card.triggerOnEndOfPlayerTurn();
            }
        }
    }


    //================== burn ======================

    @Override
    public void triggerOnTakeFromBag(AbstractCard card) {
        super.triggerOnTakeFromBag(card);
        if (BagField.isBurn()) {
            makeCardBurn(card);
        }
        if (BagField.isUpgrade()) {
            makeCardUpgrade(card);
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);

        if (BagField.isChangeToDrawPile(false)) {
            if (BagField.isUpgrade()) {
                makeCardUpgrade(card);
            }
            if (BagField.isBurn()) {
                makeCardBurn(card);
            }
        }
    }

    private void makeCardBurn(AbstractCard card) {
        flash();
//        FlavorText.AbstractCardFlavorFields.flavor.set(card, DESCRIPTIONS[1]);
//        FlavorText.AbstractCardFlavorFields.flavorBoxType.set(card, FlavorText.boxType.TRADITIONAL);
        if (!CardModifierManager.hasModifier(card, BurnModifier.ID)) {
            CardModifierManager.addModifier(card, new BurnModifier());
        }
    }

    private void makeCardUpgrade(AbstractCard card) {
        flash();
        if (card.canUpgrade()) {
            card.upgrade();
            card.superFlash();
        }
    }
}
