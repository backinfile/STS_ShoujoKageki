package ShoujoKageki_Karen.actions.bag;

import ShoujoKageki_Karen.cards.globalMove.patch.GlobalMovePatch;
import ShoujoKageki_Karen.effects.MoveCardToBagAsStarEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import ShoujoKageki_Karen.effects.MoveCardToBagEffect;

import java.util.ArrayList;
import java.util.List;

public class MoveCardToBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToBag;
    private boolean moveAsStar = false;

    public MoveCardToBagAction(List<AbstractCard> cardsToBag) {
        this(cardsToBag, false);
    }

    public MoveCardToBagAction(AbstractCard card) {
        this.cardsToBag = new ArrayList<>();
        cardsToBag.add(card);
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public MoveCardToBagAction(List<AbstractCard> cardsToBag, boolean moveAsStar) {
        this.cardsToBag = new ArrayList<>();
        this.cardsToBag.addAll(cardsToBag);
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.moveAsStar = moveAsStar;
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
                GlobalMovePatch.triggerOnPutInBag(card);
                if (!BagField.isChangeToDrawPile(false)) {
                    GlobalMovePatch.triggerGlobalMove(card, CardGroup.CardGroupType.UNSPECIFIED, GlobalMovePatch.Bag);
                }
                player.limbo.removeCard(card);
            }


            if (BagField.isChangeToDrawPile()) {
                boolean fromHand = false;
                for (AbstractCard card : cardsToBag) {
                    if (player.hand.group.remove(card)) {
                        fromHand = true;
                    }
                    bag.removeCard(card);
                    AbstractDungeon.player.hand.moveToBottomOfDeck(card);
                }
                if (fromHand) player.onCardDrawOrDiscard();
                this.addToBot(new HandCheckAction());
                isDone = true;
                return;
            }

            boolean fromHand = false;
            ArrayList<AbstractCard> cards = new ArrayList<>(cardsToBag);
            for (AbstractCard card : cards) {
                if (player.hand.group.remove(card)) fromHand = true;
                if (moveAsStar) {
                    AbstractDungeon.effectsQueue.add(new MoveCardToBagAsStarEffect(card));
                } else {
                    AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
                }
            }
            if (fromHand) player.onCardDrawOrDiscard();
            BagField.bag.get(player).group.addAll(cards);
            addToTop(new ApplyBagPowerAction(cards.size()));
        }
        tickDuration();
    }
}