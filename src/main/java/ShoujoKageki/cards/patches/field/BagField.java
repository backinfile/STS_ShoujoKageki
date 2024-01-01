package ShoujoKageki.cards.patches.field;

import ShoujoKageki.powers.BagPower;
import ShoujoKageki.powers.BurnPower;
import ShoujoKageki.powers.ReproducePower;
import ShoujoKageki.powers.VoidPower;
import ShoujoKageki.screen.BagPileViewScreen;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class BagField {
    public static SpireField<CardGroup> bag = new SpireField<>(() -> null);
    public static SpireField<Boolean> bagInfinite = new SpireField<>(() -> false);
    public static SpireField<Boolean> bagReplace = new SpireField<>(() -> false);
    public static SpireField<Boolean> bagBurn = new SpireField<>(() -> false);
    public static SpireField<Boolean> bagUpgrade = new SpireField<>(() -> false);
    public static SpireField<Boolean> bagCostZero = new SpireField<>(() -> false);

    public static CardGroup getBag() {
        return bag.get(AbstractDungeon.player);
    }

    public static boolean isInfinite(boolean flash) {
        boolean result = bagInfinite.get(AbstractDungeon.player);
        if (result && flash) flash();
        return result;
    }

    public static boolean isChangeToDrawPile(boolean flash) {
        boolean result = bagReplace.get(AbstractDungeon.player);
        if (flash && result) flash();
        return result;
    }

    public static boolean isUpgrade() {
        return BagField.bagUpgrade.get(AbstractDungeon.player);
    }

    public static boolean isBurn() {
        return BagField.bagBurn.get(AbstractDungeon.player);
    }

    public static boolean isInfinite() {
        return isInfinite(true);
    }

    public static boolean isChangeToDrawPile() {
        return isChangeToDrawPile(true);
    }

    public static boolean isCostZero() {
        return bagCostZero.get(AbstractDungeon.player);
    }

    public static void flash() {
        AbstractPower power = AbstractDungeon.player.getPower(BagPower.POWER_ID);
        if (power != null) {
            power.flash();
        }
    }

    public static boolean showCardsInBag() {
        if (BagField.isChangeToDrawPile(false)) return false;
        if (BagField.isInfinite(false)) return false;
        CardGroup bagGroup = BagField.getBag();
        if (bagGroup == null || bagGroup.isEmpty()) return false;
        return true;
    }
}
