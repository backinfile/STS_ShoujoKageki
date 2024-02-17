package ShoujoKageki.patches;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OnRelicChangePatch {
    public static void publishOnRelicChange() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;
        for (AbstractCard card : p.masterDeck.group) {
            if (card instanceof BaseCard) ((BaseCard) card).onRelicChange();
        }
        for (AbstractCard card : p.drawPile.group) {
            if (card instanceof BaseCard) ((BaseCard) card).onRelicChange();
        }
        for (AbstractCard card : p.discardPile.group) {
            if (card instanceof BaseCard) ((BaseCard) card).onRelicChange();
        }
        for (AbstractCard card : p.hand.group) {
            if (card instanceof BaseCard) ((BaseCard) card).onRelicChange();
        }
        CardGroup bag = BagField.getBag();
        if (bag != null) {
            for (AbstractCard card : bag.group) {
                if (card instanceof BaseCard) ((BaseCard) card).onRelicChange();
            }
        }
    }
}
