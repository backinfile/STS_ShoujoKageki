package ShoujoKageki_Karen.patches.showDisposeInHistory;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.annotations.SerializedName;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryPath;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.runHistory.RunPathElement;
import com.megacrit.cardcrawl.screens.stats.RunData;
import javassist.CtBehavior;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class RunDataPatch {

    @SpirePatch(
            clz = RunData.class,
            method = "<class>"
    )
    public static final class Field {
        @SerializedName("sj_disposedCards")
        public static SpireField<HashMap<String, ArrayList<String>>> disposedCards = new SpireField<>(() -> null);
        @SerializedName("sj_disposedCardsCount")
        public static SpireField<Integer> disposedCardsCount = new SpireField<>(() -> 0);
    }


    @SpirePatch2(
            clz = RunHistoryScreen.class,
            method = "reloadCards"
    )
    public static class Damage {
        public static void Postfix(RunHistoryScreen __instance, RunData runData, @ByRef String[] ___cardCountByRarityString) {
            int count = Field.disposedCardsCount.get(runData);
            if (count > 0) {
                String countString = ___cardCountByRarityString[0];
                if (StringUtils.isEmpty(countString)) {
                    countString = "";
                } else {
                    countString += ", ";
                }
                countString += String.format("%1$d%2$s", count, RunPathElementPatch.DISPOSED_COUNT_LABEL);
                ___cardCountByRarityString[0] = countString;
            }
        }
    }


    @SpirePatch2(
            clz = RunHistoryPath.class,
            method = "setRunData"
    )
    public static class SetRunData {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"element", "floor"}
        )
        public static void Insert(RunHistoryPath __instance, RunData newData, RunPathElement element, int floor) {
            HashMap<String, ArrayList<String>> disposedMap = Field.disposedCards.get(newData);
            if (disposedMap != null) {
                ArrayList<String> cards = disposedMap.get(String.valueOf(floor));
                if (cards != null) {
                    RunPathElementPatch.getDisposedCards(element).addAll(cards);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(RunPathElement.class, "col");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
