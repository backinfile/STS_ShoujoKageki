package ShoujoKageki.patches;

import ShoujoKageki.ModInfo;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.audio.TempMusic;

import java.util.HashMap;

public class AudioPatch {

    public static final String Sound_Karen_OnSelect = "Karen_OnSelect.ogg";
    public static final String Sound_Revue = "Karen_Revue.ogg";
    public static final String Sound_Last_Word = "Karen_last_word.WAV";
    public static final String Sound_Gear = "Karen_Gear.wav";
    public static final String Sound_Stage_Light = "Karen_stage_light.mp3";
    public static final String Sound_Event_FancyYou = "Karen_Event_FancyYou.ogg";
    public static final String Sound_Event_StarDiamond = "Karen_Event_StarDiamond.ogg";
    public static final String Karen_Event_Shoujo = "Karen_Event_Shoujo.ogg";
    public static final String Karen_starlight02_lightoff = "Karen_starlight02_lightoff.wav";

    public static final String Music_Form = "Karen_form.MP3";


    @SpirePatch2(
            clz = SoundMaster.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class SoundPatch {
        public static void Postfix(SoundMaster __instance, HashMap<String, Sfx> ___map) {
            load(__instance, ___map, Sound_Karen_OnSelect);
            load(__instance, ___map, Sound_Revue);
            load(__instance, ___map, Sound_Last_Word);
            load(__instance, ___map, Sound_Gear);
            load(__instance, ___map, Sound_Stage_Light);
            load(__instance, ___map, Sound_Event_FancyYou);
            load(__instance, ___map, Sound_Event_StarDiamond);
            load(__instance, ___map, Karen_Event_Shoujo);
            load(__instance, ___map, Karen_starlight02_lightoff);
        }
    }

    @SpirePatch2(
            clz = TempMusic.class,
            method = "getSong"
    )
    public static class MusicPatch {
        public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
            switch (key) {
                case Music_Form:
                    return SpireReturn.Return(MainMusic.newMusic(ModInfo.makeMusicPath(key)));
                default:
                    return SpireReturn.Continue();

            }
        }

    }

    private static void load(SoundMaster instance, HashMap<String, Sfx> ___map, String key) {
        ___map.put(key, new Sfx(ModInfo.makeSoundPath(key), false));
    }
}
