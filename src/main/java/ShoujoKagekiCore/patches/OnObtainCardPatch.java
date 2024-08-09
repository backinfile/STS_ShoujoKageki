package ShoujoKagekiCore.patches;

import ShoujoKagekiCore.base.BaseCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;

public class OnObtainCardPatch {
    @SpirePatch2(
            clz = Soul.class,
            method = "obtain"
    )
    public static class _Patch {
        public static void Postfix(AbstractCard card) {
            if (card instanceof BaseCard) {
                ((BaseCard) card).onObtainThis();
            }
        }
    }
}
