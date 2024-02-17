package ShoujoKageki_Karen.screen.patch;

import ShoujoKageki_Karen.screen.GiraffeSplashScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.splash.SplashScreen;
import javassist.CtBehavior;

public class GiraffeSplashScreenPatch {

    public static State state = State.Splash;

    public enum State {
        Splash,
        GiraffeSplash,
        Over,
    }

    public static GiraffeSplashScreen giraffeSplashScreen;

    public static void setOver() {
        state = State.Over;
    }

    @SpirePatch2(
            clz = SplashScreen.class,
            method = "update"
    )
    public static class Update {
        public static SpireReturn<Void> Prefix(SplashScreen __instance) {
            switch (state) {
                case Splash: {
                    return SpireReturn.Continue();
                }
                case GiraffeSplash: {
                    giraffeSplashScreen.update();
                    break;
                }
                case Over: {
                    __instance.isDone = true;
                }
            }
            return SpireReturn.Return();
        }

        public static void Postfix(SplashScreen __instance) {
            if (state == State.Splash && __instance.isDone) {
                state = State.GiraffeSplash;
                giraffeSplashScreen = new GiraffeSplashScreen();
                __instance.isDone = false;
            }
        }
    }


    @SpirePatch2(
            clz = SplashScreen.class,
            method = "render"
    )
    public static class Render {
        public static SpireReturn<Void> Prefix(SplashScreen __instance, SpriteBatch sb) {
            switch (state) {
                case Splash: {
                    return SpireReturn.Continue();
                }
                case GiraffeSplash: {
                    giraffeSplashScreen.render(sb);
                    break;
                }
            }
            return SpireReturn.Return();
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(SplashScreen.class, "isDone");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
