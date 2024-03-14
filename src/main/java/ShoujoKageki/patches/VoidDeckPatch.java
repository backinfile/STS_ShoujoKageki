package ShoujoKageki.patches;

import ShoujoKageki.ModInfo;
import ShoujoKageki.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.NewExpr;

public class VoidDeckPatch {

    public static boolean showVoidDeckIcon = false;
    public static Texture voidDeckIcon = TextureLoader.getTexture(ModInfo.makeUIPath("VoidDeck.png"));

    @SpirePatch2(
            clz = DrawPilePanel.class,
            method = "render"
    )
    public static class Recharge {

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    super.edit(f);
                    if (f.getClassName().equals(ImageMaster.class.getName()) && f.getFieldName().equals("DECK_BTN_BASE")) {
                        f.replace(String.format("{ $_ = (%s.showVoidDeckIcon? %s.voidDeckIcon:$proceed()); }", VoidDeckPatch.class.getName(), VoidDeckPatch.class.getName()));
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatLogic"
    )
    public static class BattleStart {
        public static void Prefix(AbstractPlayer __instance) {
            showVoidDeckIcon = false;
        }
    }
}
