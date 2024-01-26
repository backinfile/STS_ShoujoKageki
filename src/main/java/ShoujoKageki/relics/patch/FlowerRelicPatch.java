package ShoujoKageki.relics.patch;

import ShoujoKageki.Log;
import ShoujoKageki.relics.FlowerRelic;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.annotations.SerializedName;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class FlowerRelicPatch {


    @SpirePatch(
            clz = RewardItem.class,
            method = "<class>"
    )
    public static final class Field {
        @SerializedName("sj_linkBottom")
        public static SpireField<Boolean> linkBottom = new SpireField<>(() -> false);
        @SerializedName("sj_copied")
        public static SpireField<Boolean> copied = new SpireField<>(() -> false);
    }


    @SpirePatch2(
            clz = CombatRewardScreen.class,
            method = "open",
            paramtypez = {}
    )
    public static class SetupItemReward { // after loadout's patch
        public static void Postfix(CombatRewardScreen __instance) {
            addOtherOption(__instance);
        }
    }

    @SpirePatch2(
            clz = CombatRewardScreen.class,
            method = "open",
            paramtypez = {String.class}
    )
    public static class SetupItemReward2 {
        public static void Postfix(CombatRewardScreen __instance) {
            addOtherOption(__instance);
        }
    }

    @SpirePatch2(
            clz = CombatRewardScreen.class,
            method = "openCombat",
            paramtypez = {String.class, boolean.class}
    )
    public static class SetupItemReward3 {
        public static void Postfix(CombatRewardScreen __instance) {
            addOtherOption(__instance);
        }
    }

    public static void addOtherOption(CombatRewardScreen instance) {
        ArrayList<RewardItem> rewards = instance.rewards;
        AbstractRelic flowerRelic = AbstractDungeon.player.getRelic(FlowerRelic.ID);
        if (flowerRelic == null) return;

//        new RuntimeException("").printStackTrace();

        LOOP:
        while (true) {

//            StringBuilder sb = new StringBuilder("=======");
//            for (int i = 0; i < rewards.size(); i++) {
//                RewardItem rewardItem = rewards.get(i);
//                if (rewardItem.type == RewardItem.RewardType.RELIC && rewardItem.relicLink == null) {
//                    sb.append("0");
//                } else {
//                    sb.append("1");
//                }
//            }
//            Log.logger.info(sb);

            for (int i = 0; i < rewards.size(); i++) {
                RewardItem rewardItem = rewards.get(i);
                if (rewardItem.type == RewardItem.RewardType.RELIC && rewardItem.relicLink == null) {
                    AbstractRelic.RelicTier tier = rewardItem.relic.tier;
                    if (tier == AbstractRelic.RelicTier.COMMON || tier == AbstractRelic.RelicTier.UNCOMMON || tier == AbstractRelic.RelicTier.RARE) {
                        Log.logger.info("add link relic for " + rewardItem.relic.name);
                        RewardItem newRelic = new RewardItem(AbstractDungeon.returnRandomRelic(tier));
                        rewards.add(i + 1, newRelic);
                        Field.linkBottom.set(newRelic, true);
                        rewardItem.relicLink = newRelic;
                        newRelic.relicLink = rewardItem;
                        Field.copied.set(rewardItem, true);
                        Field.copied.set(newRelic, true);
                        flowerRelic.flash();
                        continue LOOP;
                    }
                }
            }
            break;
        }
        instance.positionRewards();
    }

    @SpirePatch2(
            clz = RewardItem.class,
            method = "render"
    )
    public static class Render {
        public static void Postfix(RewardItem __instance, SpriteBatch sb) throws InvocationTargetException, IllegalAccessException {
            boolean linkBottom = Field.linkBottom.get(__instance);
            if (linkBottom) {
                Method renderRelicLink = ReflectionHacks.getCachedMethod(RewardItem.class, "renderRelicLink", SpriteBatch.class);
                renderRelicLink.invoke(__instance, sb);
            }
        }
    }


    @SpirePatch2(
            clz = RewardItem.class,
            method = "render"
    )
    public static class RenderTip {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "tips"
        )
        public static void Insert(RewardItem __instance, ArrayList<PowerTip> tips) {
            if (__instance.relicLink != null && __instance.relicLink.type == RewardItem.RewardType.RELIC) {
                tips.add(new PowerTip(RewardItem.TEXT[7], RewardItem.TEXT[8] + FontHelper.colorString(__instance.relicLink.relic.name + RewardItem.TEXT[9], "y")));
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(TipHelper.class, "queuePowerTips");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = RewardItem.class,
            method = "claimReward"
    )
    public static class ClaimReward {
        public static boolean Postfix(RewardItem __instance, boolean __result) {
            if (!__result && __instance.type == RewardItem.RewardType.RELIC && __instance.relicLink != null && __instance.relicLink.type == RewardItem.RewardType.RELIC) {
                return true;
            }
            return __result;
        }

    }

}
