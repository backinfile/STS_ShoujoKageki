package ShoujoKageki.reskin.patches;

import ShoujoKageki.Log;
import ShoujoKageki.reskin.skin.SkinManager;
import ShoujoKageki.reskin.skin.SkinSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import javassist.CtBehavior;

public class SelectScreenPatch {

    public static class Lazy {

        private static final SkinSelectScreen SKIN_SELECT_SCREEN = new SkinSelectScreen();
    }

    public SelectScreenPatch() {
    }

    public static boolean isShowSkinScreen() {
        return SkinManager.isCharHasSkin(CardCrawlGame.chosenCharacter) && anySelected();
    }

    public static boolean anySelected() {
        return ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected");
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class RenderButtonPatch {
        public RenderButtonPatch() {
        }

        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (isShowSkinScreen()) {
                Lazy.SKIN_SELECT_SCREEN.render(sb);
            }

        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class UpdateButtonPatch {
        public static void Prefix(CharacterSelectScreen _inst) {
            if (isShowSkinScreen()) {
                Lazy.SKIN_SELECT_SCREEN.update();
            }
        }
    }

    @SpirePatch(
            clz = CharacterOption.class,
            method = "updateHitbox"
    )
    public static class CharacterOptionPatch_reloadAnimation {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CharacterOption __instance) {
            if (SkinManager.isCharHasSkin(CardCrawlGame.chosenCharacter)) {
                Lazy.SKIN_SELECT_SCREEN.refresh();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getPrefs");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

    }
}
