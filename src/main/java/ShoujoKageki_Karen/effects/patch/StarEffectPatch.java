package ShoujoKageki_Karen.effects.patch;

import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import javassist.CtBehavior;

public class StarEffectPatch {
    @SpirePatch(
            clz = Soul.class,
            method = "<class>"
    )
    public static class Field {
        public static SpireField<Boolean> asStar = new SpireField<>(() -> false);
    }

    @SpirePatch2(
            clz = Soul.class,
            method = "updateMovement"
    )
    public static class UpdateMovement {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(Soul __instance, Vector2 ___target, Vector2 ___pos) {
            if (!StarEffectPatch.Field.asStar.get(__instance)) {
                return;
            }
            if (__instance.isDone && !(___target.dst(___pos) < 36.0F * Settings.scale)) {
                __instance.isDone = false;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Soul.class, "vfxTimer");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
