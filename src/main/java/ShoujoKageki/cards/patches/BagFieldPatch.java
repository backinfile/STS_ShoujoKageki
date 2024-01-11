package ShoujoKageki.cards.patches;

import ShoujoKageki.actions.bag.CheckBagEmptyAction;
import ShoujoKageki.actions.bag.TakeRndTmpCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.powers.BasePower;
import ShoujoKageki.screen.BagPileViewScreen;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class BagFieldPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class CombatStart {
        public static void Prefix(AbstractPlayer p) {
            BagField.bag.set(p, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
            BagField.bagPreviewCards.set(p, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
            BagField.bagInfinite.set(p, false);
            BagField.bagReplace.set(p, false);
            BagField.bagCostZero.set(p, false);
            BagField.bagBurn.set(p, false);
            BagField.bagUpgrade.set(p, false);
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class Update {
        public static void Prefix(AbstractPlayer p) {
            if (p.hb.hovered && InputHelper.justClickedLeft && !AbstractDungeon.isScreenUp) {
//            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, "lala"));
                if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    if (BagField.showCardsInBag()) {
                        BaseMod.openCustomScreen(BagPileViewScreen.Enum.BAG_PILE_VIEW);
                    }
                }

            }
        }
    }

    @SpirePatch(
            clz = BetterDrawPileToHandAction.class,
            method = "update"
    )
    public static class DrawPileToHand {
        public static SpireReturn<Void> Prefix(BetterDrawPileToHandAction __instance,
                                               float ___duration, float ___startDuration,
                                               AbstractPlayer ___player, int ___numberOfCards,
                                               boolean ___optional) {

            if (!BagField.isChangeToDrawPile(false)) {
                return SpireReturn.Continue();
            }

            if (BagField.isInfinite(false)) {
                AbstractDungeon.actionManager.addToTop(new TakeRndTmpCardFromBagAction(___numberOfCards));
                __instance.isDone = true;
                return SpireReturn.Return();
            }

            if (___duration == ___startDuration) {
                if (___player.drawPile.isEmpty() || ___numberOfCards <= 0) {
                    return SpireReturn.Continue();
                }
                if (___player.drawPile.size() <= ___numberOfCards && !___optional) {
                    for (AbstractCard card : ___player.drawPile.group) {
                        triggerOnTakeFromBagToHand(card);
                    }
                    AbstractDungeon.actionManager.addToTop(new CheckBagEmptyAction());
                }
            } else {

                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                        triggerOnTakeFromBagToHand(card);
                    }
                    AbstractDungeon.actionManager.addToTop(new CheckBagEmptyAction());
                }
            }
            return SpireReturn.Continue();
        }


    }

//    @SpirePatch(
//            clz = DrawCardAction.class,
//            method = "endActionWithFollowUp"
//    )
//    public static class DrawCardFinish {
//        public static void Prefix(DrawCardAction instance) {
//            if (BagField.isChangeToDrawPile()) {
//                for (AbstractCard card : DrawCardAction.drawnCards) {
//                    triggerOnTakeFromBag(card);
//                }
//            }
//        }
//    }


    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update"
    )
    public static class DrawCardReplace {
        public static SpireReturn<Void> Prefix(DrawCardAction __instance, boolean ___clearDrawHistory) {
            if (BagField.isInfinite(false) && BagField.isChangeToDrawPile(false)) {
                BagField.flash();
                if (___clearDrawHistory) {
                    DrawCardAction.drawnCards.clear();
                }
                AbstractDungeon.actionManager.addToTop(new TakeRndTmpCardFromBagAction(__instance.amount, true));
                __instance.isDone = true;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    public static void triggerOnTakeFromBagToHand(AbstractCard card) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnTakeFromBag();
            ((BaseCard) card).triggerOnTakeFromBagToHand();
        }
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) ((BasePower) power).triggerOnTakeFromBag(card);
        }
    }

    public static void triggerOnTakeFromBag(AbstractCard card) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnTakeFromBag();
        }
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) ((BasePower) power).triggerOnTakeFromBag(card);
        }
    }
}
