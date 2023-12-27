package ShoujoKageki.cards.patches;

import ShoujoKageki.cards.patches.field.BagField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;


public class BagFieldPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatPreDrawLogic"
    )
    public static class CombatStart {
        public static void Prefix(AbstractPlayer p) {
            BagField.bag.set(p, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
        }
    }

}
