package ShoujoKageki.actions.bag;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.globalMove.patch.GlobalMovePatch;
import ShoujoKageki.powers.BasePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.effects.MoveCardToBagEffect;
import com.megacrit.cardcrawl.powers.AbstractPower;

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
                GlobalMovePatch.triggerGlobalMove(card, CardGroup.CardGroupType.UNSPECIFIED, GlobalMovePatch.Bag);
                for (AbstractPower power : player.powers) {
                    if (power instanceof BasePower) ((BasePower) power).triggerOnPutIntoBag(card);
                }
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
                AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
            }
            if (fromHand) player.onCardDrawOrDiscard();
            BagField.bag.get(player).group.addAll(cards);
            addToTop(new ApplyBagPowerAction(cards.size()));
        }
        tickDuration();
    }
}
