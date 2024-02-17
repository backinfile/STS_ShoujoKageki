package ShoujoKageki.karen.relics.patches;

import ShoujoKageki.relics.BaseRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;

public class LockRelicPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage",
            paramtypez = DamageInfo.class
    )
    public static class OnDead {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer __instance) {
            for (AbstractRelic relic : __instance.relics) {
                if (relic instanceof BaseRelic) ((BaseRelic) relic).triggerOnDead();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isDead");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
