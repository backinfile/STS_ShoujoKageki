package ShoujoKageki.powers;


import ShoujoKageki.actions.FormAction;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.modifier.BurnModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class FormPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(FormPower.class.getName());

    private static final String RAW_ID = FormPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public FormPower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, -1);
    }


    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new FormAction());
        flash();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }


//    @Override
//    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
//        return amount;
//    }
//
//    @Override
//    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
//        return amount;
//    }
//
//    @Override
//    public float modifyBlockLast(float blockAmount) {
//        return amount;
//    }
}
