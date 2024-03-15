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

    public static final String Music_Form = "Karen_form.ogg";


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
