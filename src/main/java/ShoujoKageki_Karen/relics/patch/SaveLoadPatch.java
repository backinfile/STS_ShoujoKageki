package ShoujoKageki_Karen.relics.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

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
