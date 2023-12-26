package ShoujoKageki.ui.patch;

import ShoujoKageki.screen.BagPileViewScreen;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import javassist.CtBehavior;
import org.apache.logging.log4j.Logger;

public class CloseScreenPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "closeCurrentScreen"
    )
    public static class CloseScreen {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert() {
            if (AbstractDungeon.screen == BagPileViewScreen.Enum.BAG_PILE_VIEW) {
                BaseMod.getCustomScreen(AbstractDungeon.screen).close();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
