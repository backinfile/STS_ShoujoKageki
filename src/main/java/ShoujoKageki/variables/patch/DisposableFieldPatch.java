package ShoujoKageki.variables.patch;

import ShoujoKageki.effects.PurgeCardInBattleEffect;
import ShoujoKageki.variables.DisposableVariable;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.mod.stslib.patches.PurgePatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import javassist.CtBehavior;
import jdk.internal.reflect.Reflection;

import java.util.ArrayList;


public class DisposableFieldPatch {
    @SpirePatch2(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class PurgePatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> poofCard(UseCardAction __instance, AbstractCard ___targetCard) {
            int curValue = DisposableField.disposable.get(___targetCard);
            if (curValue <= 0) {
                return SpireReturn.Continue();
            }
            AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(___targetCard);

            if (curValue == 1) {
                if (cardInDeck != null) {
                    AbstractDungeon.player.masterDeck.removeCard(cardInDeck);
                }
//                PurgeField.purge.set(card, true);
                AbstractDungeon.effectList.add(new PurgeCardInBattleEffect(___targetCard, ___targetCard.current_x, ___targetCard.current_y));
                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                ReflectionHacks.privateMethod(AbstractGameAction.class, "tickDuration").invoke(__instance);
                return SpireReturn.Return();
            } else {
                curValue--;
                if (cardInDeck != null) {
                    DisposableVariable.setValue(cardInDeck, curValue);
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


    // makeSameInstanceOf 保留当前闪耀计数
    // makeStatEquivalentCopy 恢复初始闪耀计数
    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeSameInstanceOf"
    )
    public static class MakeSameInstanceOf {
        @SpireInsertPatch(locator = Locator.class, localvars = "card")
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (DisposableVariable.isDisposableCard(__instance)) {
                DisposableVariable.setValue(card, DisposableVariable.getValue(__instance));
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "uuid");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


//    @SpirePatch(
//            clz = AbstractCard.class,
//            method = "makeStatEquivalentCopy"
//    )
//    public static class CardModifierStatEquivalentCopyModifiers {
//        @SpireInsertPatch(
//                locator = Locator.class,
//                localvars = {"card"}
//        )
//        public static void Insert(AbstractCard __instance, AbstractCard card) {
//            if (DisposableVariable.isDisposableCard(__instance)) {
//                DisposableVariable.setValue(card, DisposableVariable.getValue(__instance));
//            }
//        }
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//    }

//    @SpirePatch(
//            clz = CampfireSmithEffect.class,
//            method = "update"
//    )
//    public static class UpgradePatch {
//        @SpireInsertPatch(locator = UpgradeCallLocator.class)
//        public static void Insert(CampfireSmithEffect __instance, AbstractCard ___c) {
//            DisposableVariable.reset(___c);
//        }
//
//    }
//
//    @SpirePatch(
//            clz = GridCardSelectScreen.class,
//            method = "update"
//    )
//    public static class UpgradePreview {
//        @SpireInsertPatch(locator = UpgradeCallLocator.class)
//        public static void Insert(GridCardSelectScreen __instance, AbstractCard ___upgradePreviewCard) {
//            DisposableVariable.reset(___upgradePreviewCard);
//        }
//    }
//
//    private static class UpgradeCallLocator extends SpireInsertLocator {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.cards.AbstractCard", "upgrade");
//            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//        }
//    }
}
