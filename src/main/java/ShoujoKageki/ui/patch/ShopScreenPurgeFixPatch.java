package ShoujoKageki.ui.patch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;

public class ShopScreenPurgeFixPatch {


    @SpirePatch2(
            clz = ShopScreen.class,
            method = "updatePurgeCard"
    )
    public static class OnPurgeCard {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> Insert(ShopScreen __instance, float ___handY, float ___handTargetY, float ___handTimer) {
            if (___handTimer > 0.5f) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(ShopScreen.class, "purchasePurge");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
