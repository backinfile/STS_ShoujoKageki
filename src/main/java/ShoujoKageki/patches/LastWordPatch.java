package ShoujoKageki.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LastWordPatch {
    public static boolean notShowPlayerPowerTip = false;

    @SpirePatch2(clz = AbstractPlayer.class, method = "renderPowerTips")
    public static class AbstractPlayerPowerTipPatch {
        public static SpireReturn<Void> Prefix() {
            if (notShowPlayerPowerTip) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch2(clz = AbstractMonster.class, method = "renderTip")
    public static class AbstractMonsterPowerTipPatch {
        public static SpireReturn<Void> Prefix() {
            if (notShowPlayerPowerTip) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
