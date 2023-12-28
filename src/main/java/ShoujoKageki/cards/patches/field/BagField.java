package ShoujoKageki.cards.patches.field;

import ShoujoKageki.powers.BagPower;
import ShoujoKageki.powers.BurnPower;
import ShoujoKageki.powers.VoidPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class BagField {
    public static SpireField<CardGroup> bag = new SpireField<>(() -> null);
    public static SpireField<Boolean> bagInfinite = new SpireField<>(() -> false);
    public static SpireField<Boolean> bagCostZero = new SpireField<>(() -> false);

    public static CardGroup getBag() {
        return bag.get(AbstractDungeon.player);
    }

    public static boolean isInfinite() {
        return bagInfinite.get(AbstractDungeon.player);
    }

    public static boolean isChangeToDrawPile() {
        return AbstractDungeon.player.hasPower(VoidPower.POWER_ID);
    }

    public static boolean isCostZero() {
        return bagCostZero.get(AbstractDungeon.player);
    }
}
