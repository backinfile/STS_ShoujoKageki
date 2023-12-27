package ShoujoKageki.cards.patches.field;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class BagField {
    public static SpireField<CardGroup> bag = new SpireField<>(() -> null);

    public static CardGroup getBag() {
        return bag.get(AbstractDungeon.player);
    }
}
