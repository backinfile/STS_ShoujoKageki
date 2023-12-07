package thief.cards.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfCombatPreDrawLogic"
)
public class BagFieldPatch {
    public static void Prefix(AbstractPlayer p) {
        BagField.bag.set(p, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
    }
}
