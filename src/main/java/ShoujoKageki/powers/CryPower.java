package ShoujoKageki.powers;


import ShoujoKageki.Log;
import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class CryPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(CryPower.class.getName());

    private static final String RAW_ID = CryPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public CryPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

//    @Override
//    public void onCreatureApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        Log.logger.info("onCreatureApplyPower=========");
//        if (power instanceof StrengthPower && power.amount != 0) {
//            if (power.type == PowerType.DEBUFF && target.hasPower(ArtifactPower.POWER_ID)) {
//                return;
//            }
//            if (!target.isPlayer) {
//                return;
//            }
//
//            flash();
//
////            addToTop(new GainBlockAction(owner, Math.abs(power.amount) * amount));
//        }
//    }


    @Override
    public void onStrengthIncrease(int after) {
        flash();
        this.addToTop(new DamageAllEnemiesAction((AbstractCreature) null,
                DamageInfo.createDamageMatrix(after * amount, true),
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}
