package ShoujoKageki_Karen.cards.patches;

import ShoujoKageki_Karen.actions.bag.MoveCardToBagAction;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.cards.patches.field.AccretionField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AccretionFieldPatch {

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endTurn"
    )
    public static class OnTurnEnd {
        public static void Prefix() {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.isEthereal) continue;
                if (AccretionField.accretion.get(card)) {
                    if (card instanceof BaseCard) ((BaseCard) card).triggerOnAccretion();
                    AbstractDungeon.actionManager.addToBottom(new MoveCardToBagAction(card));
                }
            }
        }
    }
}
