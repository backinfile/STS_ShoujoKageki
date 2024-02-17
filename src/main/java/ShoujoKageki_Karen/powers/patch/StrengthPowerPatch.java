package ShoujoKageki_Karen.powers.patch;

import ShoujoKageki.base.BasePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import javassist.CtBehavior;

public class StrengthPowerPatch {
    @SpirePatch(
            clz = StrengthPower.class,
            method = "<class>"
    )
    public static class StrengthPowerField {
        public static SpireField<Boolean> triggerEntrance = new SpireField<>(() -> true);
    }


    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class OnApplyPower {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof BasePower) {
                    ((BasePower) power).onCreatureApplyPower(___powerToApply, __instance.target, __instance.source);
                }
            }
            if (___powerToApply instanceof StrengthPower && ___powerToApply.amount > 0) {
                AbstractPower strengthPower = __instance.target.getPower(StrengthPower.POWER_ID);
                int oldStrength = strengthPower != null ? strengthPower.amount : 0;
                for (AbstractPower power : __instance.target.powers) {
                    if (power instanceof BasePower) {
                        ((BasePower) power).onStrengthIncrease(oldStrength + ___powerToApply.amount);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }


//    @SpirePatch(
//            clz = StrengthPower.class,
//            method = "onInitialApplication"
//    )
//    public static class OnInitialApplication {
//        public static void Postfix(StrengthPower __instance) {
//            for (AbstractPower power : __instance.owner.powers) {
//                if (power instanceof BasePower) ((BasePower) power).onStrengthIncrease(__instance.amount);
//            }
//        }
//    }
//
//    @SpirePatch(
//            clz = StrengthPower.class,
//            method = "stackPower"
//    )
//    public static class StackPower {
//        public static void Postfix(StrengthPower __instance) {
//            for (AbstractPower power : __instance.owner.powers) {
//                if (power instanceof BasePower) ((BasePower) power).onStrengthIncrease(__instance.amount);
//            }
//        }
//    }
}
