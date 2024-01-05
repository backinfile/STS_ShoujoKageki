package ShoujoKageki.powers.patch;

import ShoujoKageki.Log;
import ShoujoKageki.powers.BasePower;
import ShoujoKageki.variables.patch.DisposableFieldSavePatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.HashMap;

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
                if (power instanceof BasePower)
                    ((BasePower) power).onCreatureApplyPower(___powerToApply, __instance.target, __instance.source);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}
