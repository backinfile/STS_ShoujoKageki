package ShoujoKageki.patches;

import ShoujoKageki.Log;
import ShoujoKageki.powers.BasePower;
import ShoujoKageki.util.Utils2;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import javassist.CtBehavior;

public class AttackTriggerPatch {

    @SpirePatch2(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class DamageAfter {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "damageAmount"
        )
        public static void Insert(AbstractMonster __instance, DamageInfo info, int damageAmount) {
            if (info.owner != null) {
                for (AbstractPower power : info.owner.powers) {
                    if (power instanceof BasePower) ((BasePower) power).onAttackAfter(info, damageAmount, __instance);
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "lastDamageTaken");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    @SpirePatch(
            clz = GainBlockAction.class,
            method = "<class>"
    )
    public static class FirstRunActionField {
        public static SpireField<Boolean> firstRunAction = new SpireField<>(() -> false);
    }

//    @SpirePatch2(
//            clz = ThornsPower.class,
//            method = "onAttacked"
//    )
//    public static class Test {
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static void Insert(ThornsPower __instance) {
//            new RuntimeException("onAttacked").printStackTrace();
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.NewExprMatcher(DamageAction.class);
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//    }
}
