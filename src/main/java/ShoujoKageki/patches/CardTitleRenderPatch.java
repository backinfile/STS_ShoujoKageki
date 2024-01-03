package ShoujoKageki.patches;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;

public class CardTitleRenderPatch {

    public static final float SMALLER_SCALE_CHECK = 0.8F;
    public static final float SMALLER_SCALE_APPLY = 0.72F;

    @SpirePatch(
            clz = AbstractCard.class,
            method = "<class>"
    )
    public static class Field {
        public static SpireField<Boolean> useSmallerTitleFont = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "initializeTitle"
    )
    public static class DrawCardReplace {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractCard __instance, GlyphLayout ___gl) {
            if (___gl.width > AbstractCard.IMG_WIDTH * SMALLER_SCALE_CHECK) {
                Field.useSmallerTitleFont.set(__instance, true);
            }
        }


        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(GlyphLayout.class, "reset");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderTitle"
    )
    public static class RenderTitle {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractCard __instance) {
            if (Field.useSmallerTitleFont.get(__instance)) {
                FontHelper.cardTitleFont.getData().setScale(__instance.drawScale * SMALLER_SCALE_APPLY);
            }
        }


        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "upgraded");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


//    @SpirePatch2(
//            clz = SingleCardViewPopup.class,
//            method = "renderTitle"
//    )
//    public static class SingleRenderTitle {
//        public static void Prefix(SingleCardViewPopup __instance, AbstractCard ___card) {
//            FontHelper.SCP_cardTitleFont_small.getData().setScale(Settings.scale);
//            if (___card.isSeen) {
//                if (Field.useSmallerTitleFont.get(___card)) {
//                    FontHelper.SCP_cardTitleFont_small.getData().setScale(Settings.scale * SMALLER_SCALE_APPLY);
//                }
//            }
//        }
//    }
}
