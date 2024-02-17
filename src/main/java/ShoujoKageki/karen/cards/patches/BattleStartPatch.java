package ShoujoKageki.karen.cards.patches;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class BattleStartPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatLogic"
    )
    public static class BattleStart {
        public static void Postfix(AbstractPlayer __instance) {
            final AbstractPlayer p = __instance;
            for (AbstractCard card : p.hand.group) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnBattleStart();
            }
            for (AbstractCard card : p.drawPile.group) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnBattleStart();
            }
            for (AbstractCard card : p.discardPile.group) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnBattleStart();
            }
            for (AbstractCard card : BagField.getBag().group) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnBattleStart();
            }
        }
    }
}
