package ShoujoKageki.variables.patch;

import ShoujoKageki.Log;
import ShoujoKageki.variables.DisposableVariable;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.annotations.SerializedName;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.HashMap;


public class DisposableFieldSavePatch {
    private static final String SerializedName = "ShoujoKageki:DisposableField";

    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CLASS
    )
    public static class SaveStringField { // the saved string on SaveFile
        @SerializedName(SerializedName)
        public static SpireField<ModSaves.ArrayListOfString> disposableSave = new SpireField<>(() -> null);
    }


    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class ConstructSaveFilePatch {
        public static void Prefix(SaveFile __instance, SaveFile.SaveType saveType) {
            ModSaves.ArrayListOfString saveStrings = new ModSaves.ArrayListOfString();
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                saveStrings.add(String.valueOf(DisposableVariable.getValue(card)));
            }
            SaveStringField.disposableSave.set(__instance, saveStrings);
            Log.logger.info("saveStrings = " + saveStrings);
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
            params.put(SerializedName, SaveStringField.disposableSave.get(save));
            Log.logger.info("disposableSave = " + SaveStringField.disposableSave.get(save));
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
            ModSaves.ArrayListOfString savedString = SaveStringField.disposableSave.get(CardCrawlGame.saveFile);

            CardGroup masterDeck = AbstractDungeon.player.masterDeck;
            if (savedString.size() != masterDeck.size()) {
                Log.logger.error("saved card size = " + savedString + " not matched!!");
            }

            for (int i = 0; i < savedString.size() && i < masterDeck.size(); i++) {
                int amount = Integer.parseInt(savedString.get(i));
                if (amount != 0) {
                    DisposableVariable.setValue(masterDeck.group.get(i), amount);
                }
                Log.logger.info("set value = " + amount);
            }
        }
    }

}
