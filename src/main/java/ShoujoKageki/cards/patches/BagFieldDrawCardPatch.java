package ShoujoKageki.cards.patches;

import ShoujoKageki.Log;
import ShoujoKageki.actions.bag.CheckBagEmptyAction;
import ShoujoKageki.cards.globalMove.patch.GlobalMovePatch;
import ShoujoKageki.cards.patches.field.BagField;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BagFieldDrawCardPatch {


    public static int drawCnt = 0;
    private static int beforeDrawCardCnt = 0;

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = int.class
    )
    public static class AbstractPlayerDraw {
        public static void Postfix(AbstractPlayer __instance, int numCards) {
            drawCnt += numCards;
        }
    }

    @SpirePatch(
            clz = DrawCardAction.class,
            method = "<class>"
    )
    public static class DrawCardActionField {
        public static SpireField<Boolean> ticked = new SpireField<>(() -> false);
    }

    @SpirePatch2(
            clz = DrawCardAction.class,
            method = "update"
    )
    public static class AfterDrawCard {
        public static void Prefix(DrawCardAction __instance) {
            if (!DrawCardActionField.ticked.get(__instance)) {
                beforeDrawCardCnt = drawCnt;
                DrawCardActionField.ticked.set(__instance, true);
            }
        }
    }

    @SpirePatch2(
            clz = DrawCardAction.class,
            method = "endActionWithFollowUp"
    )
    public static class AfterDrawCard2 {
        public static void Postfix(DrawCardAction __instance) {
            if (beforeDrawCardCnt != drawCnt) {
                Log.logger.info("draw " + (drawCnt - beforeDrawCardCnt) + " cards");
                if (BagField.isChangeToDrawPile(false)) {
                    AbstractDungeon.actionManager.addToTop(new CheckBagEmptyAction());
                }
            }
        }
    }
}
