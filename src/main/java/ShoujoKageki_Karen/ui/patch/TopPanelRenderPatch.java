package ShoujoKageki_Karen.ui.patch;

import ShoujoKageki.SettingsPanel;
import ShoujoKageki.base.BasePlayer;
import ShoujoKageki_Karen.character.KarenCharacter;
import ShoujoKageki_Karen.ui.TopPanelDisposedPileBtn;
import ShoujoKageki_Nana.character.NanaCharacter;
import ShoujoKageki_Nana.ui.TopPanelStageScreenBtn;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

public class TopPanelRenderPatch {

    private static final TopPanelDisposedPileBtn btn = new TopPanelDisposedPileBtn();
    private static final TopPanelStageScreenBtn stageScreenBtn = new TopPanelStageScreenBtn();


    @SpirePatch2(
            clz = TopPanel.class,
            method = "renderTopRightIcons"
    )
    public static class Render {
        public static void Postfix(SpriteBatch sb) {
            if (SettingsPanel.showDisposedPile && AbstractDungeon.player instanceof BasePlayer) {
                btn.render(sb);
            }
            if (AbstractDungeon.player instanceof NanaCharacter) {
                stageScreenBtn.render(sb);
            }
        }
    }

    @SpirePatch2(
            clz = TopPanel.class,
            method = "updateButtons"
    )
    public static class Update {
        public static void Postfix() {
            if (SettingsPanel.showDisposedPile && AbstractDungeon.player instanceof BasePlayer) {
                btn.update();
            }
            if (AbstractDungeon.player instanceof NanaCharacter) {
                stageScreenBtn.update();
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
