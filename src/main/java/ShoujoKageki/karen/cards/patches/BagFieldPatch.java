package ShoujoKageki.karen.cards.patches;

import ShoujoKageki.Log;
import ShoujoKageki.actions.bag.CheckBagEmptyAction;
import ShoujoKageki.actions.bag.TakeRndTmpCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import ShoujoKageki.effects.LightFlashPowerEffect;
import ShoujoKageki.powers.BagPower;
import ShoujoKageki.powers.BasePower;
import ShoujoKageki.relics.BaseRelic;
import ShoujoKageki.screen.BagPileViewScreen;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;


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
            float cd = BagField.bagPowerFlashCD.get(p);
            if (cd > 0) {
                BagField.bagPowerFlashCD.set(p, cd - Gdx.graphics.getDeltaTime());
            }
            if (BagFieldPatch.canFlashBagPowerOnce()) {
                if (p.hb.hovered && !AbstractDungeon.isScreenUp) {
                    tryFlashBagPowerOnce();
                }

                if (p.hoveredCard instanceof BaseCard) {
                    BaseCard hoveredCard = (BaseCard) p.hoveredCard;
                    if (hoveredCard.bagCardPreviewNumber > 0 || hoveredCard.bagCardPreviewSelectNumber > 0 || hoveredCard.bagCardPreviewExchange) {
//                        Log.logger.info("==========curhover {}", hoveredCard.name);
                        BagFieldPatch.tryFlashBagPowerOnce();
                    }
                }
            }
        }
    }

    public static boolean canFlashBagPowerOnce() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return false;
        float cd = BagField.bagPowerFlashCD.get(p);
        return cd <= 0f;
    }

    public static void tryFlashBagPowerOnce() {
        if (!canFlashBagPowerOnce()) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        BagField.bagPowerFlashCD.set(p, 1f);

        AbstractPower power = p.getPower(BagPower.POWER_ID);
        if (power != null) {
            Field effectField = ReflectionHacks.getCachedField(AbstractPower.class, "effect");
            try {
                ArrayList<AbstractGameEffect> effect = (ArrayList<AbstractGameEffect>) effectField.get(power);
                if (effect != null) {
                    effect.add(new LightFlashPowerEffect(power));
                }
            } catch (IllegalAccessException e) {
                Log.logger.error("", e);
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


    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = {int.class}
    )
    public static class PlayerDrawCardPatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "c"
        )
        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
            if (BagField.isChangeToDrawPile(false)) {
                triggerOnTakeFromBagToHand(c);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    public static void triggerOnTakeFromBagToHand(AbstractCard card) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnTakeFromBag();
            ((BaseCard) card).triggerOnTakeFromBagToHand();
        }
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) ((BaseRelic) relic).triggerOnTakeFromBag(card);
            if (relic instanceof BaseRelic) ((BaseRelic) relic).triggerOnTakeFromBagToHand(card);
        }
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) ((BasePower) power).triggerOnTakeFromBag(card);
            if (power instanceof BasePower) ((BasePower) power).triggerOnTakeFromBagToHand(card);
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


    @SpirePatch2(
            clz = AbstractRoom.class,
            method = "endTurn"
    )
    public static class EndTurnClearAttr {
        public static void Prefix() {
            if (!BagField.isChangeToDrawPile(false)) {
                CardGroup bag = BagField.getBag();
                if (bag != null) {
                    for (AbstractCard card : bag.group) {
                        card.resetAttributes();
                    }
                }
            }
        }
    }
}
