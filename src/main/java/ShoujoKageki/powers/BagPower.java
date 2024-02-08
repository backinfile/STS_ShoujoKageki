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

import static ShoujoKageki.ModInfo.DESCRIPTION;
import static ShoujoKageki.ModInfo.makeID;

public class BagPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BagPower.class.getName());

    private static final String RAW_ID = BagPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public BagPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        checkBagPower(true);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        checkBagPower(false);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkBagPower(false);
    }

    @Override
    public void updateDescription() {
        if (!(owner instanceof AbstractPlayer)) {
            this.description = DESCRIPTIONS[0];
            return;
        }
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append(DESCRIPTIONS[0]);

        boolean infinite = BagField.isInfinite(false);
        boolean changeToDrawPile = BagField.isChangeToDrawPile(false);
        boolean showCardsInBag = BagField.showCardsInBag();

        if (showCardsInBag) {
            descriptionBuilder.append(DESCRIPTIONS[1]);
        }
        if (changeToDrawPile) {
            descriptionBuilder.append(DESCRIPTIONS[3]);
        }
        if (infinite && BagField.isUpgrade()) {
            descriptionBuilder.append(DESCRIPTIONS[9]);
        } else if (infinite) {
            descriptionBuilder.append(DESCRIPTIONS[2]);
        } else if (BagField.isUpgrade()) {
            descriptionBuilder.append(DESCRIPTIONS[4]);
        }

        if (BagField.isBurn()) {
            descriptionBuilder.append(DESCRIPTIONS[5]);
        }

        if (showCardsInBag) {
            CardGroup bag = BagField.bag.get(owner);
            if (bag.isEmpty()) {
                descriptionBuilder.append(DESCRIPTIONS[6]);
            } else {
                descriptionBuilder.append(DESCRIPTIONS[7]).append(Utils2.getCardNames(bag, DESCRIPTIONS[8], false));
            }
        }

        if (descriptionBuilder.length() >= 4 && descriptionBuilder.substring(descriptionBuilder.length() - 4).equals(" NL ")) {
            descriptionBuilder.setLength(descriptionBuilder.length() - 4);
        }
        this.description = descriptionBuilder.toString();
    }

    public void checkBagPower(boolean flash) {
        int oldAmount = this.amount;
        int newAmount = BagField.showCardsInBag() ? BagField.getBag().size() : 0;
        if (newAmount == 0) newAmount = -1;

        if (oldAmount != newAmount) {
            this.amount = newAmount;
            if (flash) flash();
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
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnEndOfPlayerTurnInBag();
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
