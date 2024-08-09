package ShoujoKageki.powers;


import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.modifier.BurnModifier;
import ShoujoKagekiCore.base.BasePower;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class BurnPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BurnPower.class.getName());

    private static final String RAW_ID = BurnPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public BurnPower() {
        super(POWER_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, -1);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void triggerOnTakeFromBag(AbstractCard card) {
        super.triggerOnTakeFromBag(card);
        makeCardBurn(card);
        flash();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);

        if (BagField.isChangeToDrawPile(false)) {
            makeCardBurn(card);
        }
    }

    private static void makeCardBurn(AbstractCard card) {
        if (card.canUpgrade()) {
            card.upgrade();
        }
//        FlavorText.AbstractCardFlavorFields.flavor.set(card, DESCRIPTIONS[1]);
//        FlavorText.AbstractCardFlavorFields.flavorBoxType.set(card, FlavorText.boxType.TRADITIONAL);
        if (!CardModifierManager.hasModifier(card, BurnModifier.ID)) {
            CardModifierManager.addModifier(card, new BurnModifier());
        }
    }
}
