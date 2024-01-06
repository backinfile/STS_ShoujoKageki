package ShoujoKageki.patches;

import ShoujoKageki.powers.ReserveStrengthPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;

public class LoseStrengthPowerPatch {
    @SpirePatch(
            clz = LoseStrengthPower.class,
            method = "atEndOfTurn"
    )
    public static class AtEndOfRound {
        public static SpireReturn<Void> Prefix(LoseStrengthPower __instance) {
            AbstractPower reservePower = __instance.owner.getPower(ReserveStrengthPower.POWER_ID);
            if (reservePower != null) {
                reservePower.flash();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
