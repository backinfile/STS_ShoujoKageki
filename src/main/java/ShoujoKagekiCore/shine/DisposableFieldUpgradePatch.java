package ShoujoKagekiCore.shine;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

public class DisposableFieldUpgradePatch {


//    @SpirePatch2(
//            clz = AbstractCard.class,
//            method = "upgrade"
//    )
//    public static class Upgrade {
//        public static void Prefix(AbstractCard __instance) {
//            Log.logger.info("prefix");
//        }
//    }


//    @SpirePatch(
//            clz = AbstractCard.class,
//            method = "makeSameInstanceOf"
//    )
//    public static class MakeSameInstanceOf {
//        @SpireInsertPatch(locator = Locator.class, localvars = "card")
//        public static void Insert(AbstractCard __instance, AbstractCard card) {
//            if (DisposableVariable.isDisposableCard(__instance)) {
//                DisposableVariable.setValue(card, DisposableVariable.getValue(__instance));
//            }
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "uuid");
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy { // 复制闪耀
        @SpireInsertPatch(locator = Locator.class, localvars = "card")
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (DisposableVariable.isDisposableCard(__instance)) {
                int curValue = DisposableVariable.getValue(__instance);
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
            clz = AbstractCard.class,
            method = "upgradeName"
    )
    public static class UpgradeName { // 重置闪耀
        public static void Prefix(AbstractCard __instance) {
            DisposableVariable.reset(__instance);
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "applyPowers"
    )
    public static class ApplyPower {
        public static void Prefix(AbstractCard __instance) {
            DisposableVariable.checkModify(__instance);
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "displayUpgrades"
    )
    public static class DisplayUpgrades {
        public static void Prefix(AbstractCard __instance) {
            DisposableField.disposableModified.set(__instance, true);
        }
    }


    // TODO 神化与背包中的闪耀
//    @SpirePatch(
//            clz = ApotheosisAction.class,
//            method = "update"
//    )
//    public static class ApotheosisActionUpgrade { // 重置闪耀
//        public static void Prefix(ApotheosisAction __instance) {
//            if (BagField.isInfinite(false)) {
//                BagField.bagUpgrade.set(AbstractDungeon.player, true);
//                AbstractDungeon.actionManager.addToBottom(new ApplyBagPowerAction());
//            } else {
//                if (!BagField.isChangeToDrawPile(false)) {
//                    CardGroup bag = BagField.getBag();
//                    if (bag != null) {
//                        for (AbstractCard card : bag.group) {
//                            card.upgrade();
//                            card.superFlash();
//                            card.applyPowers();
//                        }
//                    }
//                }
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
//        private static class UpgradeCallLocator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.cards.AbstractCard", "upgrade");
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//    }
//
//    @SpirePatch(
//            clz = Whetstone.class,
//            method = "onEquip"
//    )
//    public static class UpgradePatch2 {
//        @SpireInsertPatch(locator = BeforeArrayCallLocator.class, localvars = "upgradableCards")
//        public static void Insert(Whetstone __instance, ArrayList<AbstractCard> upgradableCards) {
//            for (AbstractCard card : upgradableCards) {
//                DisposableVariable.reset(card);
//            }
//        }
//    }
//
//    @SpirePatch(
//            clz = WarPaint.class,
//            method = "onEquip"
//    )
//    public static class UpgradePatch3 {
//        @SpireInsertPatch(locator = BeforeArrayCallLocator.class, localvars = "upgradableCards")
//        public static void Insert(WarPaint __instance, ArrayList<AbstractCard> upgradableCards) {
//            for (AbstractCard card : upgradableCards) {
//                DisposableVariable.reset(card);
//            }
//        }
//    }
//
//    private static class BeforeArrayCallLocator extends SpireInsertLocator {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher("java.util.ArrayList", "isEmpty");
//            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//        }
//    }


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
