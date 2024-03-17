package ShoujoKageki.screen.patch;

import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.screen.KarenCutscene;
import ShoujoKageki.screen.KarenCutsceneVideoScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class KarenVideoCutscenePatch {

    @SpirePatch2(
            clz = TrueVictoryRoom.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class StartPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getClassName().equals(Cutscene.class.getName())) {
                        e.replace(String.format("{ $_ = (%s.player.chosenClass == %s.Enums.Karen)? new %s($$): $proceed($$); }",
                                AbstractDungeon.class.getName(),
                                KarenCharacter.class.getName(),
                                KarenCutscene.class.getName())
                        );
                    }
                }
            };
        }
    }


    @SpirePatch2(
            clz = TrueVictoryRoom.class,
            method = "onPlayerEntry"
    )
    public static class SilencePatch {
        public static void Postfix() {
            if (AbstractDungeon.player.chosenClass == KarenCharacter.Enums.Karen) {
                CardCrawlGame.music.silenceBGMInstantly();
                CardCrawlGame.music.silenceTempBgmInstantly();
            }
        }
    }

    @SpirePatch2(
            clz = VictoryScreen.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DynamicBannerPatch {
        public static void Postfix() {
            if (AbstractDungeon.player.chosenClass == KarenCharacter.Enums.Karen) {
                AbstractDungeon.dynamicBanner.appear(KarenCutscene.UI_STRINGS.TEXT[1]);
            }
        }
    }

    @SpirePatch2(
            clz = VictoryScreen.class,
            method = "reopen",
            paramtypez = {boolean.class}
    )
    public static class DynamicBannerPatch2 {
        public static void Postfix() {
            if (AbstractDungeon.player.chosenClass == KarenCharacter.Enums.Karen) {
                AbstractDungeon.dynamicBanner.appear(KarenCutscene.UI_STRINGS.TEXT[1]);
            }
        }
    }
}
