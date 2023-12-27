package ShoujoKageki.cards.patches.field;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractCard.class,
        method = "<class>"
)
public class PutToBagField {
    public static SpireField<Boolean> putToBag = new SpireField<>(() -> {
        return false;
    });
}
