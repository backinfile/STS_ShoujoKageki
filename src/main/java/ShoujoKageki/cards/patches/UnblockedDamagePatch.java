package ShoujoKageki.cards.patches;

import ShoujoKageki.Log;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

public class UnblockedDamagePatch {
    public static int UnblockedDamageCurCount = 0;

    @SpirePatch2(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class Damage {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "damageAmount"
        )
        public static void Insert(AbstractMonster __instance, DamageInfo info, int damageAmount) {
            if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL) {
                UnblockedDamageCurCount++;
                Log.logger.info("UnblockedDamageCurCount = " + UnblockedDamageCurCount);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                int[] allInOrder = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{allInOrder[1]};
            }
        }
    }

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class ApplyStartOfTurnRelics {
        public static void Prefix() {
            UnblockedDamageCurCount = 0;
            Log.logger.info("reset UnblockedDamageCurCount");
        }
    }
}
