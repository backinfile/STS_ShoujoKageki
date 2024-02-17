package ShoujoKageki_Karen.patches;

import ShoujoKageki.util.Utils2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.relics.CeramicFish;

public class CeramicFishPatch {
    @SpirePatch(
            clz = CeramicFish.class,
            method = "onObtainCard"
    )
    public static class OnObtainCard {
        public static SpireReturn<Void> Prefix(CeramicFish __instance) {
            if (Utils2.inBattlePhase()) return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }
}
