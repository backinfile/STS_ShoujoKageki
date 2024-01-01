package ShoujoKageki.variables.patch;

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

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy { // 当前卡的闪耀值突破上限了，保留突破上限的这些
        @SpireInsertPatch(locator = Locator.class, localvars = "card")
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            int curValue = DisposableVariable.getValue(__instance);
            int newValue = DisposableVariable.getValue(card);
            if (curValue > newValue && newValue != -1) {
                DisposableVariable.setValue(card, curValue);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = CampfireSmithEffect.class,
            method = "update"
    )
    public static class UpgradePatch {
        @SpireInsertPatch(locator = UpgradeCallLocator.class)
        public static void Insert(CampfireSmithEffect __instance, AbstractCard ___c) {
            DisposableVariable.reset(___c);
        }

        private static class UpgradeCallLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.cards.AbstractCard", "upgrade");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = Whetstone.class,
            method = "onEquip"
    )
    public static class UpgradePatch2 {
        @SpireInsertPatch(locator = BeforeArrayCallLocator.class, localvars = "upgradableCards")
        public static void Insert(Whetstone __instance, ArrayList<AbstractCard> upgradableCards) {
            for (AbstractCard card : upgradableCards) {
                DisposableVariable.reset(card);
            }
        }
    }

    @SpirePatch(
            clz = WarPaint.class,
            method = "onEquip"
    )
    public static class UpgradePatch3 {
        @SpireInsertPatch(locator = BeforeArrayCallLocator.class, localvars = "upgradableCards")
        public static void Insert(WarPaint __instance, ArrayList<AbstractCard> upgradableCards) {
            for (AbstractCard card : upgradableCards) {
                DisposableVariable.reset(card);
            }
        }
    }

    private static class BeforeArrayCallLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher("java.util.ArrayList", "isEmpty");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
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
}
