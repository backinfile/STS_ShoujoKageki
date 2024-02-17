package ShoujoKageki.karen.cards.patches;

import ShoujoKageki.karen.cards.patches.field.ExpectField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;


public class ExpectFieldPatch {
    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class AfterUse {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> poofCard(UseCardAction __instance, AbstractCard ___targetCard) {
            return SpireReturn.Continue();

//            if (!ExpectField.expect.get(___targetCard)) {
//                return SpireReturn.Continue();
//            }
//            __instance.exhaustCard = true;
//            AbstractDungeon.player.onCardDrawOrDiscard();
//            AbstractDungeon.effectList.add(new ExhaustCardEffect(___targetCard));
//            AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
//            ReflectionHacks.privateMethod(AbstractGameAction.class, "tickDuration").invoke(__instance);
//            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(UseCardAction.class, "exhaustCard");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


    @SpirePatch(
            clz = UseCardAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCard.class, AbstractCreature.class}
    )
    public static class BeforeUseOtherCard {
        public static void Postfix(UseCardAction __instance, AbstractCard card) {
            // for other expect card in hand
            for (AbstractCard handCard : AbstractDungeon.player.hand.group) {
                if (handCard == card) continue;
                if (!ExpectField.expect.get(handCard)) continue;
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(handCard, AbstractDungeon.player.hand));
            }
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endTurn"
    )
    public static class OnTurnEnd {
        public static void Prefix() {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.isEthereal) continue;
                if (ExpectField.expect.get(card)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
            }
        }
    }
}
