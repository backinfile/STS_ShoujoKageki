package ShoujoKageki.cards.patches;

import ShoujoKageki.Log;
import ShoujoKageki.actions.bag.CheckBagEmptyAction;
import ShoujoKageki.cards.patches.field.BagField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

public class BagFieldDrawCardPatch {


    public static int drawCnt = 0;
    private static int beforeDrawCardCnt = 0;

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = int.class
    )
    public static class AbstractPlayerDraw {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer __instance) {
            drawCnt += 1;
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
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
                Log.logger.info("DrawCardAction: draw " + (drawCnt - beforeDrawCardCnt) + " cards");
                if (BagField.isChangeToDrawPile(false)) {
                    AbstractDungeon.actionManager.addToTop(new CheckBagEmptyAction());
                }
            } else {
                Log.logger.info("DrawCardAction: draw 0 cards");
            }
        }
    }


    @SpirePatch(
            clz = PlayTopCardAction.class,
            method = "update"
    )
    public static class PlayTopCardActionPatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "card"
        )
        public static void Insert(PlayTopCardAction __instance, AbstractCard card) {
            if (BagField.isChangeToDrawPile(false)) {
                BagFieldPatch.triggerOnTakeFromBag(card);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "remove");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
