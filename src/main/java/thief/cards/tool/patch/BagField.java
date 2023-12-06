package thief.cards.tool.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class BagField {
    public static SpireField<CardGroup> bag = new SpireField<>(() -> {
        return new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    });
}
