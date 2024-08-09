package ShoujoKagekiCore.shine.showDisposeInHistory;

import ShoujoKagekiCore.base.BaseRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveFilePatch {
    private static final String SerializedName1 = "ShoujoKagekiCore:metric_disposedCards";
    private static final String SerializedName2 = "ShoujoKagekiCore:metric_disposedCardsCount";

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
            for (AbstractRelic relic : p.relics) {
                if (relic instanceof BaseRelic) ((BaseRelic) relic).onSaveLoad();
            }
        }
    }
}
