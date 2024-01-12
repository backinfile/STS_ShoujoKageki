package ShoujoKageki.patches;

import ShoujoKageki.powers.BasePower;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
}
