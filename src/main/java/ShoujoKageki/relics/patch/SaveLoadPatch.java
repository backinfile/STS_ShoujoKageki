package ShoujoKageki.relics.patch;

import ShoujoKageki.Log;
import ShoujoKageki.patches.showDisposeInHistory.MetricDataPatch;
import ShoujoKageki.patches.showDisposeInHistory.SaveFilePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveLoadPatch {

    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "loadPlayerSave"
    )
    public static class LoadPlayerSaves {
        public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
        }
    }
}
