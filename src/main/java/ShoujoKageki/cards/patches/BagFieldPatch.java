package ShoujoKageki.cards.patches;

import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.screen.BagPileViewScreen;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class BagFieldPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatPreDrawLogic"
    )
    public static class CombatStart {
        public static void Prefix(AbstractPlayer p) {
            BagField.bag.set(p, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
            BagField.bagInfinite.set(p, false);
            BagField.bagCostZero.set(p, false);
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class Update {
        public static void Prefix(AbstractPlayer p) {
            if (p.hb.hovered && InputHelper.justClickedLeft) {
//            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, "lala"));
                if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    if (!BagField.getBag().isEmpty() && !BagField.isInfinite()) {
                        BaseMod.openCustomScreen(BagPileViewScreen.Enum.BAG_PILE_VIEW);
                    }
                }

            }
        }
    }

}
