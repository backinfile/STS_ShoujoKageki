package ShoujoKageki.ui.patch;

import ShoujoKageki.SettingsPanel;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.ui.TopPanelDisposedPileBtn;
import basemod.TopPanelGroup;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;

public class TopPanelRenderPatch {

    private static final TopPanelDisposedPileBtn btn = new TopPanelDisposedPileBtn();


    @SpirePatch2(
            clz = TopPanel.class,
            method = "renderTopRightIcons"
    )
    public static class Render {
        public static void Postfix(SpriteBatch sb) {
            if (SettingsPanel.showDisposedPile && AbstractDungeon.player instanceof KarenCharacter) {
                btn.render(sb);
            }
        }
    }

    @SpirePatch2(
            clz = TopPanel.class,
            method = "updateButtons"
    )
    public static class Update {
        public static void Postfix() {
            if (SettingsPanel.showDisposedPile && AbstractDungeon.player instanceof KarenCharacter) {
                btn.update();
            }
        }
    }


//    @SpirePatch2(
//            clz = TopPanelGroup.class,
//            method = "updateItems"
//    )
//    public static class OpenExhaustPileViewScreenPatch {
//        @SpireInsertPatch(
//                locator = Locator.class,
//                localvars = "xpos"
//        )
//        public static void Insert(TopPanelGroup _instance, @ByRef float[] xpos) {
//            xpos[0] = (1667.0F + 64f) * Settings.scale;
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctBehavior) throws Exception {
//                Matcher matcher = new Matcher.FieldAccessMatcher(TopPanelGroup.class, "activePanelItems");
//                return LineFinder.findInOrder(ctBehavior, matcher);
//            }
//        }
//    }
}
