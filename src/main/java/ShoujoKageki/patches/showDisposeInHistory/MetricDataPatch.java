package ShoujoKageki.patches.showDisposeInHistory;

import ShoujoKageki.relics.BaseRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetricDataPatch {
    public static final int RECORD_MAX = 20;

    @SpirePatch(
            clz = MetricData.class,
            method = "<class>"
    )
    public static final class Field {
        public static SpireField<HashMap<String, ArrayList<String>>> disposedCards = new SpireField<>(() -> null);
        public static SpireField<Integer> disposedCardsCount = new SpireField<>(() -> 0);
    }


    public static HashMap<String, ArrayList<String>> getDisposedCards(MetricData data) {
        HashMap<String, ArrayList<String>> strings = Field.disposedCards.get(data);
        if (strings == null) {
            strings = new HashMap<>();
            Field.disposedCards.set(data, strings);
        }
        return strings;
    }

    public static void addDisposedCard(AbstractCard card) {
        HashMap<String, ArrayList<String>> disposedCards = getDisposedCards(CardCrawlGame.metricData);
        String floor = String.valueOf(AbstractDungeon.floorNum);
        ArrayList<String> disposed = disposedCards.computeIfAbsent(floor, k -> new ArrayList<>());
        if (disposed.size() < RECORD_MAX) {
            disposed.add(card.getMetricID());
        }

        int oldValue = Field.disposedCardsCount.get(CardCrawlGame.metricData);
        Field.disposedCardsCount.set(CardCrawlGame.metricData, oldValue + 1);
    }


    @SpirePatch2(
            clz = Metrics.class,
            method = "gatherAllData",
            paramtypez = {boolean.class, boolean.class, MonsterGroup.class}
    )
    public static class Damage {
        public static void Postfix(Metrics __instance, HashMap<Object, Object> ___params) {
            ___params.put("sj_disposedCards", getDisposedCards(CardCrawlGame.metricData));
            ___params.put("sj_disposedCardsCount", Field.disposedCardsCount.get(CardCrawlGame.metricData));
        }
    }

    @SpirePatch2(
            clz = MetricData.class,
            method = "clearData"
    )
    public static class ClearData {
        public static void Postfix(MetricData __instance) {
            HashMap<String, ArrayList<String>> disposedCards = Field.disposedCards.get(__instance);
            if (disposedCards != null) {
                disposedCards.clear();
            }
            Field.disposedCardsCount.set(__instance, 0);

        }
    }

    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "loadPlayerSave"
    )
    public static class LoadPlayerSaves {
        public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
            HashMap<String, ArrayList<String>> disposedCards = getDisposedCards(CardCrawlGame.metricData);
            disposedCards.putAll(SaveFilePatch.SaveStringField.disposedCards.get(CardCrawlGame.saveFile));
            Field.disposedCardsCount.set(CardCrawlGame.metricData, SaveFilePatch.SaveStringField.disposedCardsCount.get(CardCrawlGame.saveFile));
        }
    }
}
