package ShoujoKageki.variables.patch;

import ShoujoKageki.Log;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.PurgeCardInBattleEffect;
import ShoujoKageki.variables.DisposableVariable;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import javassist.CtBehavior;

import java.util.ArrayList;


public class DisposableFieldPatch {
    @SpirePatch2(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class AfterUsePatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> poofCard(UseCardAction __instance, AbstractCard ___targetCard) {
            int curValue = DisposableField.disposable.get(___targetCard);
            if (curValue <= 0) {
                return SpireReturn.Continue();
            }
            AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(___targetCard);

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
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(UseCardAction.class, "exhaustCard");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

}
