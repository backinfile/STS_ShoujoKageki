package ShoujoKageki.cards.patches.field;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "<class>"
)
public class AccretionField {
    public static SpireField<Boolean> accretion = new SpireField<>(() -> false);
}
