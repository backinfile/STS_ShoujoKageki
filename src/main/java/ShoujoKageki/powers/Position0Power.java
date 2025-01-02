package ShoujoKageki.powers;


import ShoujoKageki.cards.patches.BagFieldPatch;
import ShoujoKageki.cards.patches.UnblockedDamagePatch;
import ShoujoKageki.effects.LightFlashPowerEffect;
import ShoujoKageki.patches.AttackTriggerPatch;
import ShoujoKageki.powers.patch.StrengthPowerPatch;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static ShoujoKageki.ModInfo.makeID;

public class Position0Power extends BasePower {
    public static final Logger logger = LogManager.getLogger(Position0Power.class.getName());

    private static final String RAW_ID = Position0Power.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public Position0Power(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void onAttackAfter(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            if (UnblockedDamagePatch.UnblockedDamageCurCount <= this.amount) {
                GainBlockAction gainBlockAction = new GainBlockAction(this.owner, this.owner, damageAmount);
                AttackTriggerPatch.FirstRunActionField.firstRunAction.set(gainBlockAction, true);
                addToTop(gainBlockAction);
            }
        }

        // move gain block action to top. fix aoe to ThornsPower
        if (AbstractDungeon.actionManager.actions.stream().anyMatch(a -> a instanceof GainBlockAction &&
                AttackTriggerPatch.FirstRunActionField.firstRunAction.get(a))) {
            List<AbstractGameAction> gainBlockActions = AbstractDungeon.actionManager.actions.stream().filter(a -> a instanceof GainBlockAction &&
                    AttackTriggerPatch.FirstRunActionField.firstRunAction.get(a)).collect(Collectors.toList());
            AbstractDungeon.actionManager.actions.removeAll(gainBlockActions);
            for (AbstractGameAction a: gainBlockActions) {
                AbstractDungeon.actionManager.addToTop(a);
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
    }

    //    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        super.onApplyPower(power, target, source);
//        if (target == this.owner) {
//            if (power instanceof StrengthPower) {
//                if (StrengthPowerPatch.StrengthPowerField.triggerEntrance.get(power)) {
//                    if (power.amount > 0) {
//                        StrengthPower powerToApply = new StrengthPower(this.owner, amount);
//                        StrengthPowerPatch.StrengthPowerField.triggerEntrance.set(powerToApply, false);
//                        addToBot(new ApplyPowerAction(this.owner, this.owner, powerToApply));
//                        flash();
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    private float timer = 0f;

    @Override
    public void update(int slot) {
        super.update(slot);
        timer -= Gdx.graphics.getDeltaTime();
        if (timer <= 0) {
            timer = 1f;

            if (UnblockedDamagePatch.UnblockedDamageCurCount < this.amount) {
                BagFieldPatch.flashPower(this);
            }
        }
    }
}
