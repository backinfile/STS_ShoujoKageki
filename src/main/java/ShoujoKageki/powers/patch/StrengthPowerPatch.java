package ShoujoKageki.powers.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StrengthPowerPatch {
    @SpirePatch(
            clz = StrengthPower.class,
            method = "<class>"
    )
    public static class StrengthPowerField {
        public static SpireField<Boolean> triggerEntrance = new SpireField<>(() -> true);
    }
}
