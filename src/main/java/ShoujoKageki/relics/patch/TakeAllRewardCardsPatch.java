package ShoujoKageki.relics.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SingingBowl;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import ShoujoKageki.Log;
import ShoujoKageki.relics.BookMarchRelic;
import ShoujoKageki.ui.TakeAllRewardCardsButton;
import javassist.CtBehavior;

import java.lang.reflect.Field;

public class TakeAllRewardCardsPatch {
    private static final TakeAllRewardCardsButton takeAllRewardCardsButton = new TakeAllRewardCardsButton();


    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "open"
    )
    public static class OpenPatch {
        public static void Postfix(CardRewardScreen __instance) {
            Log.logger.info("========= openPatch");
            fixTakeAllButtonsShow(__instance);
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "reopen"
    )
    public static class ReopenPatch {
        public static void Postfix(CardRewardScreen __instance) {
            Log.logger.info("========= reopenPatch");
            fixTakeAllButtonsShow(__instance);
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "update"
    )
    public static class UpdatePatch {
        public static void Postfix(CardRewardScreen __instance, AbstractCard ___touchCard) {
            if (Settings.isTouchScreen) {
                if (__instance.confirmButton.hb.clicked && ___touchCard != null) {
                    takeAllRewardCardsButton.hide();
                }
            }

            if (!PeekButton.isPeeking) {
                takeAllRewardCardsButton.update();
            }
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "render"
    )
    public static class RenderPatch {
        public static void Prefix(CardRewardScreen __instance, SpriteBatch sb) {
            if (!PeekButton.isPeeking) {
                takeAllRewardCardsButton.render(sb);
            }
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "customCombatOpen"
    )
    public static class CustomCombatOpen {
        public static void Postfix(CardRewardScreen __instance) {
            takeAllRewardCardsButton.hide();
        }
    }
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "chooseOneOpen"
    )
    public static class ChooseOneOpen {
        public static void Postfix(CardRewardScreen __instance) {
            takeAllRewardCardsButton.hide();
        }
    }
    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "draftOpen"
    )
    public static class DraftOpen {
        public static void Postfix(CardRewardScreen __instance) {
            takeAllRewardCardsButton.hide();
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "cardSelectUpdate"
    )
    public static class CardSelectUpdate {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CardRewardScreen __instance) {
            takeAllRewardCardsButton.hide();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SingingBowlButton.class, "hide");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    private static void fixTakeAllButtonsShow(CardRewardScreen instance) {
        takeAllRewardCardsButton.hide();

        AbstractRelic takeAllCardsRelic = AbstractDungeon.player.getRelic(BookMarchRelic.ID);
        if (takeAllCardsRelic == null || takeAllCardsRelic.usedUp) {
            return;
        }

        SkipCardButton ___skipButton = null;
        SingingBowlButton ___bowlButton = null;

        {
            try {
                Field skipButtonField = CardRewardScreen.class.getDeclaredField("skipButton");
                Field bowlButtonField = CardRewardScreen.class.getDeclaredField("bowlButton");
                skipButtonField.setAccessible(true);
                bowlButtonField.setAccessible(true);
                ___skipButton = (SkipCardButton) skipButtonField.get(instance);
                ___bowlButton = (SingingBowlButton) bowlButtonField.get(instance);
            } catch (Exception e) {
                Log.logger.error(e);
                return;
            }
        }


        boolean skipBtnHidden = isHidden(SkipCardButton.class, ___skipButton);
        if (skipBtnHidden) {
            takeAllRewardCardsButton.hide();
            Log.logger.info("skipBtnHidden");
            return;
        }
        boolean bowlBtnHidden = isHidden(SingingBowlButton.class, ___bowlButton);

        takeAllRewardCardsButton.show(instance.rItem);
        if (bowlBtnHidden) { // only skipBtn and takeAllBtn
            Log.logger.info("only skipBtn and takeAllBtn");
            setTargetX(SkipCardButton.class, ___skipButton, TakeAllRewardCardsButton.SHOW_X - 165.0F * Settings.scale);
            setTargetX(SingingBowlButton.class, ___bowlButton, TakeAllRewardCardsButton.SHOW_X + 165.0F * Settings.scale);
            takeAllRewardCardsButton.target_x = TakeAllRewardCardsButton.SHOW_X + 165.0F * Settings.scale;
        } else { // three btn
            Log.logger.info("three btn");
            setTargetX(SkipCardButton.class, ___skipButton, TakeAllRewardCardsButton.SHOW_X - 165.0F * Settings.scale * 2);
            setTargetX(SingingBowlButton.class, ___bowlButton, TakeAllRewardCardsButton.SHOW_X);
            takeAllRewardCardsButton.target_x = TakeAllRewardCardsButton.SHOW_X + 165.0F * Settings.scale * 2;
        }

    }

    private static <T> void setTargetX(Class<T> clazz, T instance, float value) {
        try {
            Field target_x = clazz.getDeclaredField("target_x");
            target_x.setAccessible(true);
            target_x.set(instance, value);
        } catch (Exception e) {
            Log.logger.error(e);
        }
    }

    private static <T> boolean isHidden(Class<T> clazz, T instance) {
        try {
            Field isHidden = clazz.getDeclaredField("isHidden");
            isHidden.setAccessible(true);
            return isHidden.getBoolean(instance);
        } catch (Exception e) {
            Log.logger.error(e);
            return true;
        }
    }
}
