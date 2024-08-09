package ShoujoKagekiCore.shine;

import ShoujoKagekiCore.Log;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        public static SpireField<ModSaves.ArrayListOfString> counter = new SpireField<>(() -> null);
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class Field { // the saved string on SaveFile
        public static SpireField<ModSaves.ArrayListOfString> counter = new SpireField<>(() -> null);
    }

    public static int getDiffShineDisposedCount() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            ModSaves.ArrayListOfString strings = Field.counter.get(p);
            if (strings != null) {
                return strings.size();
            }
        }
        return 0;
    }

    public static void addShineCardDispose(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            ModSaves.ArrayListOfString strings = Field.counter.get(p);
            if (strings == null) {
                strings = new ModSaves.ArrayListOfString();
                strings.add(card.cardID);
                Field.counter.set(p, strings);
            } else {
                if (!strings.contains(card.cardID)) strings.add(card.cardID);
            }
        }
    }


    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class ConstructSaveFilePatch {
        public static void Prefix(SaveFile __instance, SaveFile.SaveType saveType) {
            ModSaves.ArrayListOfString value = Field.counter.get(AbstractDungeon.player);
            ModSaves.ArrayListOfString copy = new ModSaves.ArrayListOfString();
            if (value != null) copy.addAll(value);
            SaveStringField.counter.set(__instance, copy);
            Log.logger.info("collect " + SerializedName + " = " + copy);
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
            ModSaves.ArrayListOfString value = SaveStringField.counter.get(CardCrawlGame.saveFile);
            ModSaves.ArrayListOfString copy = new ModSaves.ArrayListOfString();
            if (value != null) copy.addAll(value);
            Field.counter.set(AbstractDungeon.player, copy);
            Log.logger.info("load " + SerializedName + " = " + copy);
        }
    }
}
