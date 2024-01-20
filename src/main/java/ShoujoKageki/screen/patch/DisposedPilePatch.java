package ShoujoKageki.screen.patch;

import ShoujoKageki.ModInfo;
import ShoujoKageki.variables.patch.DisposableField;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

public class DisposedPilePatch {

    public static boolean showScreen = false;
    private static boolean showingScreen = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(DisposedPilePatch.class.getSimpleName()));



    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "render"
    )
    public static class RenderExhaustPileViewScreenPatch {
        @SpireInsertPatch(
                locator = RenderExhaustPileViewScreenPatchLocator.class
        )
        public static SpireReturn<Void> Insert(ExhaustPileViewScreen _instance, SpriteBatch sb) {
            if (showingScreen) {
                FontHelper.renderDeckViewTip(sb, uiStrings.TEXT[0], 96.0F * Settings.scale, Settings.CREAM_COLOR);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
        private static class RenderExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderDeckViewTip");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "open"
    )
    public static class OpenExhaustPileViewScreenPatch {
        @SpireInsertPatch(
                locator = OpenExhaustPileViewScreenPatchLocator.class
        )
        public static void Insert(ExhaustPileViewScreen _instance) {
            if (showScreen) {
                CardGroup group = (CardGroup) ReflectionHacks.getPrivate(_instance, ExhaustPileViewScreen.class, "exhaustPileCopy");
                group.clear();
                group.group.addAll(DisposableField.getDisposedPile().group);
                showScreen = false;
                showingScreen = true;
            } else {
                showingScreen = false;
            }

        }

        private static class OpenExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(ExhaustPileViewScreen.class, "hideCards");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
