package ShoujoKageki.actions.bag;

import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.BagDefendPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.effects.MoveCardToBagEffect;

import java.util.ArrayList;

public class MoveCardToBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToBag;

    public MoveCardToBagAction(ArrayList<AbstractCard> cardsToBag) {
        this.cardsToBag = new ArrayList<>();
        this.cardsToBag.addAll(cardsToBag);
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public MoveCardToBagAction(AbstractCard card) {
        this.cardsToBag = new ArrayList<>();
        cardsToBag.add(card);
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (cardsToBag.isEmpty()) {
                isDone = true;
                return;
            }

            CardGroup bag = BagField.getBag();
            AbstractPlayer player = AbstractDungeon.player;

            for (AbstractCard card : cardsToBag) {
                if (card instanceof BaseCard) ((BaseCard) card).triggerOnPutInBag();
            }


            if (BagField.isChangeToDrawPile()) {
                for (AbstractCard card : cardsToBag) {
                    player.hand.group.remove(card);
                    bag.removeCard(card);
                    AbstractDungeon.player.hand.moveToBottomOfDeck(card);
                }
                this.addToBot(new HandCheckAction());
                isDone = true;
                return;
            }

            ArrayList<AbstractCard> cards = new ArrayList<>(cardsToBag);
            for (AbstractCard card : cards) {
                player.hand.group.remove(card);
                AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
            }
            BagField.bag.get(player).group.addAll(cards);
            addToTop(new ApplyBagPowerAction(cards.size()));
        }
        tickDuration();
    }
}
