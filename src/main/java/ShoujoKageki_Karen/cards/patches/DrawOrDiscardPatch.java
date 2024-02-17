package ShoujoKageki_Karen.cards.patches;

import ShoujoKageki_Karen.cards.bag.Parry;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class DrawOrDiscardPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onCardDrawOrDiscard"
    )
    public static class OnCardDrawOrDiscard {
        public static void Prefix(AbstractPlayer __instance) {
            for (AbstractCard card : __instance.hand.group) {
                if (card instanceof Parry) {
                    ((Parry) card).triggerOnPlayerDrawOrDiscard();
                }
            }
        }
    }
}
