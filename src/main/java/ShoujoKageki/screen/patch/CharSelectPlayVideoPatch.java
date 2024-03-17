package ShoujoKageki.screen.patch;

import ShoujoKageki.cards.patches.ExpectFieldPatch;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.reskin.patches.SelectScreenPatch;
import ShoujoKageki.screen.CharSelectPlayVideoScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import javassist.CtBehavior;

public class CharSelectPlayVideoPatch {
    public static boolean inPlayVideo = false;

    public static class Lazy {
        public static final CharSelectPlayVideoScreen PLAY_VIDEO_SCREEN = new CharSelectPlayVideoScreen();
    }

    @SpirePatch2(
            clz = CharacterOption.class,
            method = "updateHitbox"
    )
    public static class OnSelected {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert() {
            if (CardCrawlGame.chosenCharacter == KarenCharacter.Enums.Karen) {
                Lazy.PLAY_VIDEO_SCREEN.start();
            } else if (inPlayVideo) {
                Lazy.PLAY_VIDEO_SCREEN.overInstantly();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getPrefs");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    @SpirePatch2(
            clz = CharacterSelectScreen.class,
            method = "updateButtons"
    )
    public static class CancelPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert() {
            if (inPlayVideo) {
                CharSelectPlayVideoPatch.Lazy.PLAY_VIDEO_SCREEN.overInstantly();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(MainMenuScreen.class, "superDarken");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class UpdatePatch {
        public static void Prefix() {
            if (inPlayVideo) {
                CharSelectPlayVideoPatch.Lazy.PLAY_VIDEO_SCREEN.update();
            }
        }
    }

    @SpirePatch2(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class RenderPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(SpriteBatch sb) {
            if (inPlayVideo) {
                CharSelectPlayVideoPatch.Lazy.PLAY_VIDEO_SCREEN.render(sb);
            }

        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CharacterSelectScreen.class, "cancelButton");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = CharacterSelectScreen.class,
            method = "updateButtons"
    )
    public static class EnterDungeonPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert() {
            if (inPlayVideo) {
                CharSelectPlayVideoPatch.Lazy.PLAY_VIDEO_SCREEN.overInstantly();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "generateSeeds");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = CharacterSelectScreen.class,
            method = "open"
    )
    public static class ResetPatch {
        public static void Postfix() {
            CharSelectPlayVideoScreen.lastShowVideoTime = 0;
        }
    }
}
