package ShoujoKageki_Karen.patches;

import ShoujoKageki_Karen.cards.patches.field.BagField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class UnceasingTopPatch {

    @SpirePatch(clz = UnceasingTop.class, method = "onRefreshHand")
    public static class OnRefreshHand { // 打出无限能力后，陀螺可无限抽牌
        public static void Prefix(UnceasingTop __instance, boolean ___canDraw, boolean ___disabledUntilEndOfTurn) {
            if (AbstractDungeon.actionManager.actions.isEmpty()
                    && AbstractDungeon.player.hand.isEmpty()
                    && !AbstractDungeon.actionManager.turnHasEnded
                    && ___canDraw && !AbstractDungeon.player.hasPower("No Draw")
                    && !AbstractDungeon.isScreenUp
                    && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                    && !___disabledUntilEndOfTurn
            ) {
                // && (AbstractDungeon.player.discardPile.size() > 0 || AbstractDungeon.player.drawPile.size() > 0)
                if (!(AbstractDungeon.player.discardPile.size() > 0 || AbstractDungeon.player.drawPile.size() > 0)) {
                    if (BagField.isInfinite(false) && BagField.isChangeToDrawPile()) {
                        __instance.flash();
                        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, __instance));
                        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
                    }
                }
            }
        }
    }
}
