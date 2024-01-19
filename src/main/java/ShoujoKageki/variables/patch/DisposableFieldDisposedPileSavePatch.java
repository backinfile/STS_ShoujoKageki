package ShoujoKageki.variables.patch;

import ShoujoKageki.Log;
import ShoujoKageki.patches.TokenCardField;
import ShoujoKageki.util.ArrayListOfCardSave;
import ShoujoKageki.variables.DisposableVariable;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.HashMap;

public class DisposableFieldDisposedPileSavePatch {

    private static final String SerializedName = "ShoujoKageki:DisposedPile";

    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CLASS
    )
    public static class SaveStringField { // the saved string on SaveFile
        @com.google.gson.annotations.SerializedName(SerializedName)
        public static SpireField<ArrayListOfCardSave> saveOnFile = new SpireField<>(() -> null);
    }


    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class ConstructSaveFilePatch { // 构造存储变量
        public static void Prefix(SaveFile __instance, SaveFile.SaveType saveType) {
            ArrayListOfCardSave cardSaves = new ArrayListOfCardSave();
            for (AbstractCard card : DisposableField.getDisposedPile().group) {
                cardSaves.add(new CardSave(card.cardID, card.timesUpgraded, card.misc));
            }
            SaveStringField.saveOnFile.set(__instance, cardSaves);
        }
    }


    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "save",
            paramtypez = {SaveFile.class}
    )
    public static class Save { // 构造json文件
        @SpireInsertPatch(
                locator = SaveLocator.class,
                localvars = {"params"}
        )
        public static void Insert(SaveFile save, HashMap<String, Object> params) {
            params.put(SerializedName, SaveStringField.saveOnFile.get(save));
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
    public static class LoadPlayerSaves { // 加载存储文件
        public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
            ArrayListOfCardSave cardSaves = SaveStringField.saveOnFile.get(CardCrawlGame.saveFile);
            if (cardSaves != null) {
                for (CardSave s : cardSaves) {
                    AbstractCard card = CardLibrary.getCopy(s.id, s.upgrades, s.misc);
                    TokenCardField.isToken.set(card, false);
                    DisposableField.getDisposedPile().group.add(card);
                }
            }
        }
    }

}
