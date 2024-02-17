package ShoujoKageki.util;

import ShoujoKageki.Log;
import ShoujoKageki_Karen.ShoujoKagekiKarenManager;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.MonsterHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class GenTool {
    private static final Gson gson = new Gson();

    public static void gen() {
//        gen_monster_group();
    }

    private static void gen_card_rarity() {
        HashMap<String, String> map = new HashMap<>();
        for (AbstractCard card : ShoujoKagekiKarenManager.allKarenCardList) {
            map.put(card.cardID, card.rarity.name());
        }
        Log.logger.info(gson.toJson(map));
    }

    private static void gen_monster_group() {
        ArrayList<String> monsters = new ArrayList<>();
        for (Field field : MonsterHelper.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                if (field.getType() == String.class) {
                    try {
                        monsters.add((String) field.get(null));
                    } catch (Exception e) {
                        Log.logger.error("", e);
                    }
                }
            }
        }
        Log.logger.info(gson.toJson(monsters));
    }
}
