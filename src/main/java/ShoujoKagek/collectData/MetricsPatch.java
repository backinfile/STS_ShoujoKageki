package ShoujoKagek.collectData;


import ShoujoKageki.Log;
import ShoujoKageki.character.KarenCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.GameOverScreen;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MetricsPatch {
    private static final Gson gson = new Gson();
    private static final String URL = "http://59.110.33.80:12007";
    private static final String URL1 = "http://127.0.0.1:12007";

    private static void sendPost(HashMap<Object, Object> params) {
        String url = URL;
        HashMap<String, Serializable> event = new HashMap<>();
        event.put("event", params);
        if (Settings.isBeta) {
            event.put("host", CardCrawlGame.playerName);
        } else {
            event.put("host", CardCrawlGame.alias);
        }

        event.put("time", System.currentTimeMillis() / 1000L);
        String data = gson.toJson(event);
        Log.logger.info("UPLOADING METRICS TO: url=" + url + ",data=" + data);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method("POST").url(url).header("Content-Type", "application/json").header("Accept", "application/json").header("User-Agent", "curl/7.43.0").build();
        httpRequest.setContent(data);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Log.logger.info("Metrics: http request response: " + httpResponse.getResultAsString());
            }

            public void failed(Throwable t) {
                Log.logger.info("Metrics: http request failed: " + t.toString());
            }

            public void cancelled() {
                Log.logger.info("Metrics: http request cancelled.");
            }
        });
    }

    @SpirePatch(
            clz = Metrics.class,
            method = "run"
    )
    public static class RunPatch {
        @SpirePostfixPatch
        public static void Postfix(Metrics __instance, HashMap<Object, Object> ___params) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass == KarenCharacter.Enums.Karen) {
                try {
                    Method m = Metrics.class.getDeclaredMethod("gatherAllData", Boolean.TYPE, Boolean.TYPE, MonsterGroup.class);
                    m.setAccessible(true);
                    m.invoke(__instance, __instance.death, __instance.trueVictory, __instance.monsters);
                    ___params.put("language", Settings.language.name());
                    ___params.put("mods", Arrays.stream(Loader.MODINFOS).map((info) -> info.Name).sorted().collect(Collectors.toCollection(ArrayList::new)));
                    sendPost(___params);
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var3) {
                    var3.printStackTrace();
                }
            }

        }
    }

    @SpirePatch(
            clz = GameOverScreen.class,
            method = "shouldUploadMetricData"
    )
    public static class shouldUploadMetricData {
        @SpirePostfixPatch
        public static boolean Postfix(boolean __retVal) {
            if (AbstractDungeon.player.chosenClass == KarenCharacter.Enums.Karen) {
                __retVal = Settings.UPLOAD_DATA;
            }

            return __retVal;
        }
    }
}
