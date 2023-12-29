package ShoujoKageki.patches;

import ShoujoKageki.cards.BaseCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class TurnEndPatch {
    @SpirePatch(
            clz = AbstractCreature.class,
            method = "applyEndOfTurnTriggers"
    )
    public static class EndTurn {
        public static void Prefix(AbstractCreature __instance) {
            if (!(__instance instanceof AbstractPlayer)) return;

            AbstractPlayer p = (AbstractPlayer) __instance;
            for (AbstractCard card : p.discardPile.group) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnEndOfPlayerTurnInDiscardPile();
            }
            for (AbstractCard card : p.drawPile.group) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnEndOfPlayerTurnInDrawPile();
            }
        }
    }
}
