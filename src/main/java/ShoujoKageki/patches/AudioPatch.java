package ShoujoKageki.patches;

import ShoujoKageki.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;

import java.util.HashMap;

public class AudioPatch {

    public static final String Karen_OnSelect = "Karen_OnSelect.ogg";
    public static final String Revue = "Revue.ogg";
    public static final String Last_Word = "last_word_bgm_fixed.WAV";

    @SpirePatch(
            clz = SoundMaster.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class SoundPatch {
        public static void Postfix(SoundMaster instance, HashMap<String, Sfx> ___map) {
            load(instance, ___map, Karen_OnSelect);
            load(instance, ___map, Revue);
            load(instance, ___map, Last_Word);
        }
    }

    private static void load(SoundMaster instance, HashMap<String, Sfx> ___map, String key) {
        ___map.put(key, new Sfx(ModInfo.makeAudioPath("/sound/" + key), false));
    }
}
