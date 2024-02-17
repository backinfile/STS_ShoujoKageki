package ShoujoKageki_Karen.patches;

import ShoujoKageki.FTUEUtils;
import ShoujoKageki_Karen.character.KarenCharacter;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.TipTracker;

public class TipTrackerPatch {


    @SpirePatch2(
            clz = TipTracker.class,
            method = "refresh"
    )
    public static class DamageAfter {
        public static void Postfix() {
            TipTracker.tips.put(FTUEUtils.COMBAT_TIP_KEY, TipTracker.pref.getBoolean(FTUEUtils.COMBAT_TIP_KEY, false));
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class CreateDrawPile {
        public static void Postfix(AbstractPlayer __instance) {
            if (__instance instanceof KarenCharacter) {
                FTUEUtils.openCombatTip();
            }
        }
    }
}
