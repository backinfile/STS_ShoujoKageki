package ShoujoKageki.cards.patches;

import ShoujoKageki.cards.BaseCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class CryPatch {
    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class Shuffle01 {
        public static void Postfix(EmptyDeckShuffleAction __instance) {
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof BaseCard) {
                    ((BaseCard) card).triggerOnShuffleInfoDrawPile();
                }
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAction.class,
            method = "update"
    )
    public static class Shuffle02 {
        public static void Prefix(ShuffleAction __instance) {
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof BaseCard) {
                    ((BaseCard) card).triggerOnShuffleInfoDrawPile();
                }
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAllAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class Shuffle03 {
        public static void Postfix(ShuffleAllAction __instance) {
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof BaseCard) {
                    ((BaseCard) card).triggerOnShuffleInfoDrawPile();
                }
            }
        }
    }

    @SpirePatch(
            clz = Soul.class,
            method = "update"
    )
    public static class MoveToDiscardPile {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(Soul __instance) {
            AbstractCard card = __instance.card;
            if (card instanceof BaseCard) {
                ((BaseCard) card).triggerWhenMoveToDiscardPile();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "teleportToDiscardPile");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

}
