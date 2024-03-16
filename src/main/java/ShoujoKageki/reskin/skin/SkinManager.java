package ShoujoKageki.reskin.skin;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.character.KarenCharacter;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkinManager {

    public static final boolean HIDE_SELECT_SCREEN = false;

    public static final HashMap<AbstractPlayer.PlayerClass, List<AbstractSkin>> skinMap = new HashMap<>();
    public static final HashMap<String, Integer> skinSelected = new HashMap<>();
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(SkinManager.class.getSimpleName()));

    public static void init() {
        Log.logger.info("init skin");
//        addSkin(KarenCharacter.Enums.Karen, new AbstractSkin(
//                "fan",
//                ModInfo.getResPath("/images/char/nana/Nana.atlas"),
//                ModInfo.getResPath("/images/char/nana/Nana.json"),
//                UI_STRINGS.TEXT[1],
//                UI_STRINGS.EXTRA_TEXT[1],
//                "")
//        );
        addSkin(KarenCharacter.Enums.Karen, new AbstractSkin(
                "fan",
                ModInfo.getResPath("/images/char/karen/skin_fan/Karen.atlas"),
                ModInfo.getResPath("/images/char/karen/skin_fan/Karen.json"),
                UI_STRINGS.TEXT[0],
                UI_STRINGS.EXTRA_TEXT[0],
                "")
        );
//        addSkin(KarenCharacter.Enums.Karen, new AbstractSkin(
//                "tv_clip",
//                ModInfo.getResPath("/images/char/karen/skin_tv/Karen.atlas"),
//                ModInfo.getResPath("/images/char/karen/skin_tv/Karen.json"),
//                UI_STRINGS.TEXT[0],
//                UI_STRINGS.EXTRA_TEXT[0],
//                "")
//        );
//        addSkin(KarenCharacter.Enums.Karen, new AbstractSkin(
//                "pixel",
//                ModInfo.getResPath("/images/char/karen/skin_pixel/Karen.atlas"),
//                ModInfo.getResPath("/images/char/karen/skin_pixel/Karen.json"),
//                UI_STRINGS.TEXT[1],
//                UI_STRINGS.EXTRA_TEXT[1],
//                "") // https://www.pixiv.net/artworks/70941447
//        );
        BaseMod.addSaveField(ModInfo.makeID("reskin"), new CustomSavable<HashMap<String, Integer>>() {
            @Override
            public HashMap<String, Integer> onSave() {
                return skinSelected;
            }

            @Override
            public void onLoad(HashMap<String, Integer> stringIntegerHashMap) {
                skinSelected.clear();
                if (stringIntegerHashMap != null) {
                    skinSelected.putAll(stringIntegerHashMap);
                }
                loadSkin(AbstractDungeon.player);
            }
        });
        Log.logger.info("init skin finish");
    }

    public static boolean isCharHasSkin(AbstractPlayer.PlayerClass playerClass) {
        return skinMap.containsKey(playerClass);
    }

    public static void addSkin(AbstractPlayer.PlayerClass playerClass, AbstractSkin skin) {
        skinMap.computeIfAbsent(playerClass, k -> new ArrayList<>()).add(skin);
    }

    public static int getCurSkinIndex(AbstractPlayer.PlayerClass playerClass) {
        List<AbstractSkin> abstractSkins = skinMap.get(playerClass);
        if (abstractSkins == null || abstractSkins.isEmpty()) return 0;
        if (skinSelected.containsKey(playerClass.name())) {
            int selectIndex = skinSelected.get(playerClass.name());
            if (selectIndex >= 0 && selectIndex < abstractSkins.size()) {
                return selectIndex;
            }
        }
        return 0;
    }

    public static AbstractSkin getCurSkin(AbstractPlayer.PlayerClass playerClass) {
        List<AbstractSkin> abstractSkins = skinMap.get(playerClass);
        if (abstractSkins == null || abstractSkins.isEmpty()) return null;
        return abstractSkins.get(getCurSkinIndex(playerClass));
    }

    public static void loadSkin(AbstractPlayer player) {
        if (player == null) return;
        AbstractSkin curSkin = getCurSkin(player.chosenClass);
        if (curSkin != null) {
            Log.logger.info("{} load skin {}", player.chosenClass, curSkin.SkinId);
            curSkin.player = player;
            curSkin.onLoad();
        }
    }
}
