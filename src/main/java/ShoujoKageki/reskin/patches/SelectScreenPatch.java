package ShoujoKageki.reskin.patches;

import ShoujoKageki.reskin.skin.SkinManager;
import ShoujoKageki_Karen.screen.SkinSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

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
}
