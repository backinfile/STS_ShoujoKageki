package ShoujoKageki.patches.showDisposeInHistory;

import ShoujoKageki.Log;
import ShoujoKageki.variables.patch.DisposableFieldCounterSavePatch;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveFilePatch {
    private static final String SerializedName1 = "ShoujoKageki:metric_disposedCards";
    private static final String SerializedName2 = "ShoujoKageki:metric_disposedCardsCount";

    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CLASS
    )
    public static class SaveStringField { // the saved string on SaveFile
        @com.google.gson.annotations.SerializedName(SerializedName1)
        public static SpireField<HashMap<String, ArrayList<String>>> disposedCards = new SpireField<>(() -> null);
        @com.google.gson.annotations.SerializedName(SerializedName2)
        public static SpireField<Integer> disposedCardsCount = new SpireField<>(() -> 0);
    }


    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class ConstructSaveFilePatch {
        public static void Prefix(SaveFile __instance, SaveFile.SaveType saveType) {
            SaveStringField.disposedCards.set(__instance, MetricDataPatch.getDisposedCards(CardCrawlGame.metricData));
            SaveStringField.disposedCardsCount.set(__instance, MetricDataPatch.Field.disposedCardsCount.get(CardCrawlGame.metricData));
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "save",
            paramtypez = {SaveFile.class}
    )
    public static class Save {
        @SpireInsertPatch(
                locator = SaveLocator.class,
                localvars = {"params"}
        )
        public static void Insert(SaveFile save, HashMap<String, Object> params) {
            params.put(SerializedName1, SaveStringField.disposedCards.get(save));
            params.put(SerializedName2, SaveStringField.disposedCardsCount.get(save));
        }

        private static class SaveLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher("com.google.gson.GsonBuilder", "create");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "loadPlayerSave"
    )
    public static class LoadPlayerSaves {
        public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
            HashMap<String, ArrayList<String>> stringArrayListHashMap = SaveStringField.disposedCards.get(CardCrawlGame.saveFile);
            if (stringArrayListHashMap != null) {
                MetricDataPatch.Field.disposedCards.set(CardCrawlGame.metricData, stringArrayListHashMap);
            }

            int count = SaveStringField.disposedCardsCount.get(CardCrawlGame.saveFile);
            MetricDataPatch.Field.disposedCardsCount.set(CardCrawlGame.metricData, count);

            Log.logger.info("load " + SerializedName1 + " = " + stringArrayListHashMap);
            Log.logger.info("load " + SerializedName2 + " = " + count);
        }
    }
}
