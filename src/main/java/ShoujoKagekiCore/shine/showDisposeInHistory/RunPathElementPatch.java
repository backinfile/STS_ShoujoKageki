package ShoujoKagekiCore.shine.showDisposeInHistory;

import ShoujoKagekiCore.CoreModPath;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.runHistory.RunPathElement;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.List;

public class RunPathElementPatch {
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(CoreModPath.makeID("RunPathElement"));
    public static final String DISPOSED_CARD_LABEL = UI_STRINGS.TEXT[0];
    public static final String DISPOSED_COUNT_LABEL = UI_STRINGS.TEXT[1];


    @SpirePatch(
            clz = RunPathElement.class,
            method = "<class>"
    )
    public static final class Field {
        public static SpireField<List<String>> disposedCards = new SpireField<>(() -> null);

    }

    public static List<String> getDisposedCards(RunPathElement element) {
        List<String> strings = Field.disposedCards.get(element);
        if (strings == null) {
            strings = new ArrayList<>();
            Field.disposedCards.set(element, strings);
        }
        return strings;
    }


    @SpirePatch2(
            clz = RunPathElement.class,
            method = "getTipDescriptionText"
    )
    public static class Damage {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "sb"
        )
        public static void Insert(RunPathElement __instance, StringBuilder sb) {

            List<String> disposedCards = getDisposedCards(__instance);
            if (!disposedCards.isEmpty()) {
                String TEXT_OBTAIN_TYPE_CARD = ReflectionHacks.getPrivateStatic(RunPathElement.class, "TEXT_OBTAIN_TYPE_CARD");
                sb.append(" NL ");
                sb.append(DISPOSED_CARD_LABEL);
                sb.append(" NL ");
                for (String key : disposedCards) {
                    String text = CardLibrary.getCardNameFromMetricID(key);
                    sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_CARD).append(text);
                    sb.append(" NL ");
                }
                sb.setLength(sb.length() - 4);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(StringBuilder.class, "length");
                int[] allInOrder = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{allInOrder[allInOrder.length - 1]};
            }
        }
    }
}
