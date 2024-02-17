package ShoujoKageki_Karen.variables.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class DisposableField2 {
    public static SpireField<CardGroup> disposedPile = new SpireField<>(() -> null);

}
