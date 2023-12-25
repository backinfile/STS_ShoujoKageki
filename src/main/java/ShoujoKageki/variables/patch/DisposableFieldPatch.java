package ShoujoKageki.variables.patch;

import ShoujoKageki.variables.DisposableVariable;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;


public class DisposableFieldPatch {


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class UseCard {
        public static void Prefix(AbstractPlayer p, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            int curValue = DisposableField.disposable.get(card);
            if (curValue <= 0) {
                return;
            }
            AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(card);

            if (curValue == 1) {
                PurgeField.purge.set(card, true);
                if (cardInDeck != null) {
                    AbstractDungeon.player.masterDeck.removeCard(cardInDeck);
                }
            } else {
                curValue--;
                if (cardInDeck != null) {
                    DisposableVariable.setValue(cardInDeck, curValue);
                }
                DisposableVariable.setValue(card, curValue);
            }
        }
    }


    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class CardModifierStatEquivalentCopyModifiers {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"card"}
        )
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (DisposableVariable.isDisposableCard(__instance)) {
                DisposableVariable.setValue(card, DisposableVariable.getValue(__instance));
            }
        }


        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
