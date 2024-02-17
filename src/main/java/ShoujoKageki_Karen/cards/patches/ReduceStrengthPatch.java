package ShoujoKageki_Karen.cards.patches;

import ShoujoKageki_Karen.powers.ReduceStrengthLimitPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class ReduceStrengthPatch {


    @SpirePatch(
            clz = GainStrengthPower.class,
            method = "atEndOfTurn"
    )
    public static class OnTurnEnd {

        public static SpireReturn<Void> Prefix(GainStrengthPower instance) {
            AbstractCreature owner = instance.owner;
            int amount = instance.amount;
            if (!owner.isPlayer) {
                AbstractPower limitPower = AbstractDungeon.player.getPower(ReduceStrengthLimitPower.POWER_ID);
                if (limitPower != null && amount > limitPower.amount) { // has GainStrengthPower more than 5
                    int recover = limitPower.amount;
                    limitPower.flash();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, recover), recover));
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, GainStrengthPower.POWER_ID, recover));
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = GainStrengthPower.class,
            method = "updateDescription"
    )
    public static class UpdateDescription {

        public static SpireReturn<Void> Prefix(GainStrengthPower instance) {
            AbstractCreature owner = instance.owner;
            int amount = instance.amount;
            if (!owner.isPlayer) {
                AbstractPower limitPower = AbstractDungeon.player.getPower(ReduceStrengthLimitPower.POWER_ID);
                if (limitPower != null && amount > limitPower.amount) { // has GainStrengthPower more than 5
                    int recover = limitPower.amount;
                    instance.description = GainStrengthPower.DESCRIPTIONS[0] + recover + GainStrengthPower.DESCRIPTIONS[1] +
                            "(" + ReduceStrengthLimitPower.NAME + ")";
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }
}
