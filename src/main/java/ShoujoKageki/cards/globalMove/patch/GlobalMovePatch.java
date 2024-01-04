package ShoujoKageki.cards.globalMove.patch;

import ShoujoKageki.Log;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.powers.BasePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

// to hand: draw select
// to drawPile: shuffle
// to discardPile: discard
// to bag: moveToBag
// to exhaustPile: exhaust
public class GlobalMovePatch {
    public static void triggerGlobalMove(AbstractCard c, CardGroup.CardGroupType from, CardGroup.CardGroupType to) {
        if (c instanceof BaseCard) {
            ((BaseCard) c).triggerOnGlobalMove();
            if (((BaseCard) c).logGlobalMove) {
                Log.logger.info("GlobalMove card:" + c.name + " from:" + from.name() + " to:" + to.name());
            }
        }
    }

    public static void triggerOnPutInBag(AbstractCard card) {
        if (card instanceof BaseCard) ((BaseCard) card).triggerOnPutInBag();
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) ((BasePower) power).triggerOnPutIntoBag(card);
        }
    }

    public static void triggerShuffleInfoDrawPile(AbstractCard card) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnShuffleInfoDrawPile();
        }
        if (BagField.isChangeToDrawPile(false)) {
            triggerOnPutInBag(card);
        }
    }

    @SpireEnum(name = "Bag")
    public static CardGroup.CardGroupType Bag;


//    @SpirePatch(
//            clz = AbstractPlayer.class,
//            method = "draw",
//            paramtypez = int.class
//    )
//    public static class Draw {
//        @SpireInsertPatch(
//                locator = Locator.class,
//                localvars = "c"
//        )
//        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
//            triggerGlobalMove(c, CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.HAND);
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerWhenDrawn");
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//
//    }


    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class Shuffle01 {
        public static void Postfix(EmptyDeckShuffleAction __instance) {
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                triggerGlobalMove(card, CardGroup.CardGroupType.DISCARD_PILE, CardGroup.CardGroupType.DRAW_PILE);
                triggerShuffleInfoDrawPile(card);
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAction.class,
            method = "update"
    )
    public static class Shuffle02 {
        public static void Prefix(ShuffleAction __instance, boolean ___triggerRelics) {
            if (___triggerRelics) {
                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    triggerGlobalMove(card, CardGroup.CardGroupType.DISCARD_PILE, CardGroup.CardGroupType.DRAW_PILE);
                    triggerShuffleInfoDrawPile(card);
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
                triggerGlobalMove(card, CardGroup.CardGroupType.DISCARD_PILE, CardGroup.CardGroupType.DRAW_PILE);
                triggerShuffleInfoDrawPile(card);
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
            triggerGlobalMove(card, CardGroup.CardGroupType.UNSPECIFIED, CardGroup.CardGroupType.DISCARD_PILE);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "teleportToDiscardPile");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }


    @SpirePatch(
            clz = CardGroup.class,
            method = "moveToExhaustPile",
            paramtypez = AbstractCard.class
    )
    public static class Exhaust {
        public static void Postfix(CardGroup __instance, AbstractCard ___c) {
            triggerGlobalMove(___c, CardGroup.CardGroupType.UNSPECIFIED, CardGroup.CardGroupType.EXHAUST_PILE);
        }
    }

    @SpirePatch(
            clz = CardGroup.class,
            method = "moveToHand",
            paramtypez = AbstractCard.class
    )
    public static class ToHand01 {
        public static void Postfix(CardGroup __instance, AbstractCard ___c) {
            triggerGlobalMove(___c, CardGroup.CardGroupType.UNSPECIFIED, CardGroup.CardGroupType.HAND);
        }
    }

    @SpirePatch(
            clz = CardGroup.class,
            method = "moveToHand",
            paramtypez = {AbstractCard.class, CardGroup.class}
    )
    public static class ToHand02 {
        public static void Postfix(CardGroup __instance, AbstractCard ___c) {
            triggerGlobalMove(___c, CardGroup.CardGroupType.UNSPECIFIED, CardGroup.CardGroupType.HAND);
        }
    }

    @SpirePatch(
            clz = CardGroup.class,
            method = "addToHand",
            paramtypez = {AbstractCard.class}
    )
    public static class ToHand03 {
        public static void Postfix(CardGroup __instance, AbstractCard ___c) {
            triggerGlobalMove(___c, CardGroup.CardGroupType.UNSPECIFIED, CardGroup.CardGroupType.HAND);
        }
    }
}
