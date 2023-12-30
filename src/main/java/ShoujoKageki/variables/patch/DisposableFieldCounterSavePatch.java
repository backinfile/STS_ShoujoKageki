package ShoujoKageki.variables.patch;

import ShoujoKageki.Log;
import ShoujoKageki.variables.DisposableVariable;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.HashMap;

public class DisposableFieldCounterSavePatch {
    private static final String SerializedName = "ShoujoKageki:DisposableFieldCounter";

    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CLASS
    )
    public static class SaveStringField { // the saved string on SaveFile
        @com.google.gson.annotations.SerializedName(SerializedName)
        public static SpireField<Integer> counter = new SpireField<>(() -> 0);
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class Field { // the saved string on SaveFile
        public static SpireField<Integer> counter = new SpireField<>(() -> 0);
    }


    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class ConstructSaveFilePatch {
        public static void Prefix(SaveFile __instance, SaveFile.SaveType saveType) {
            Integer value = Field.counter.get(AbstractDungeon.player);
            SaveStringField.counter.set(__instance, value);
            Log.logger.info("collect " + SerializedName + " = " + value);
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
            params.put(SerializedName, SaveStringField.counter.get(save));
            Log.logger.info("save " + SerializedName + " = " + SaveStringField.counter.get(save));
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
            Integer value = SaveStringField.counter.get(CardCrawlGame.saveFile);
            Field.counter.set(AbstractDungeon.player, value);
            Log.logger.info("load " + SerializedName + " = " + value);
        }
    }
}
