package ShoujoKagekiCore.shine;

import ShoujoKagekiCore.util.Utils2;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;


public class DisposableFieldPatch {
    @SpirePatch2(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class AfterUsePatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> Insert(UseCardAction __instance, AbstractCard ___targetCard) {
            int curValue = DisposableField.disposable.get(___targetCard);
            if (curValue <= 0) {
                return SpireReturn.Continue();
            }
            AbstractCard cardInDeck = Utils2.getMasterDeckEquivalent(___targetCard);

            if (curValue == 1 || DisposableField.forceDispose.get(___targetCard)) {

                DisposableField.disposeCard(___targetCard);
                AbstractDungeon.player.onCardDrawOrDiscard();

                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                ReflectionHacks.privateMethod(AbstractGameAction.class, "tickDuration").invoke(__instance);
                return SpireReturn.Return();
            } else {
                curValue--;
                if (cardInDeck != null) { // 牌组中的闪耀值可能低于战斗内的，此时不扣
                    if (DisposableVariable.getValue(cardInDeck) > curValue) {
                        DisposableVariable.setValue(cardInDeck, curValue);
                    }
                }
                DisposableVariable.setValue(___targetCard, curValue);
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(UseCardAction.class, "exhaustCard");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


    @SpirePatch2(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class Use_Power {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(UseCardAction __instance, AbstractCard ___targetCard) {
            int curValue = DisposableField.disposable.get(___targetCard);
            if (curValue <= 0) return;

            AbstractCard cardInDeck = Utils2.getMasterDeckEquivalent(___targetCard);
            if (curValue == 1 || DisposableField.forceDispose.get(___targetCard)) {
                DisposableField.disposeCard(___targetCard);
            } else {
                curValue--;
                if (cardInDeck != null) { // 牌组中的闪耀值可能低于战斗内的，此时不扣
                    if (DisposableVariable.getValue(cardInDeck) > curValue) {
                        DisposableVariable.setValue(cardInDeck, curValue);
                    }
                }
                DisposableVariable.setValue(___targetCard, curValue);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "empower");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
