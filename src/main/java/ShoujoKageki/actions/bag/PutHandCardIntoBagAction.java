package ShoujoKageki.actions.bag;

import ShoujoKageki.actions.PutBagCardsToHandAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ShoujoKageki.cards.patches.field.BagField;

import java.util.ArrayList;
import java.util.List;

import static ShoujoKageki.ModInfo.makeID;

public class PutHandCardIntoBagAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(PutHandCardIntoBagAction.class.getName());

    private static final String ID = makeID(PutHandCardIntoBagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiString.TEXT;

    private final AbstractPlayer player;
    private boolean allCardInHand = false;
    private boolean replaceAllCardInHand = false;

    public PutHandCardIntoBagAction(AbstractPlayer player, int amount) {
        this.player = player;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    // all card in hand
    public PutHandCardIntoBagAction(AbstractPlayer player, boolean allCardInHand, boolean replaceAllCardInHand) {
        this(player, 1);
        this.allCardInHand = allCardInHand;
        this.replaceAllCardInHand = replaceAllCardInHand;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (replaceAllCardInHand) {
                replaceAllCardInHand();
                isDone = true;
                return;
            }
            if (player.hand.isEmpty()) {
                isDone = true;
                return;
            }
            if (allCardInHand) {
                bagCards(player.hand.group);
                isDone = true;
                return;
            }
            if (player.hand.size() <= this.amount) {
                bagCards(player.hand.group);
                isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            CardGroup selectedCards = AbstractDungeon.handCardSelectScreen.selectedCards;
            if (selectedCards.isEmpty()) {
                isDone = true;
                return;
            }
            bagCards(selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }

    private void replaceAllCardInHand() {
        if (BagField.isChangeToDrawPile()) {
            ArrayList<AbstractCard> cardsInBag = new ArrayList<>(AbstractDungeon.player.drawPile.group);
            AbstractDungeon.player.drawPile.group.clear();

            bagCards(player.hand.group);


            if (BagField.isInfinite()) {
                addToBot(new TakeRndTmpCardFromBagAction(cardsInBag.size()));
                return;
            }
            addToBot(new PutBagCardsToHandAction(player, cardsInBag));
            return;
        }


        CardGroup bag = BagField.bag.get(player);
        int beforeAmount = bag.size();

        ArrayList<AbstractCard> bagCards = new ArrayList<>(bag.group);
        bag.clear();
        addToBot(new ApplyBagPowerAction(-beforeAmount));
        bagCards(player.hand.group);

        if (BagField.isInfinite()) {
            addToBot(new TakeRndTmpCardFromBagAction(BaseMod.MAX_HAND_SIZE));
            return;
        }
//        addToBot(new PutHandCardIntoBagAction(player, true, false));
        addToBot(new PutBagCardsToHandAction(player, bagCards));
    }

    private void bagCards(List<AbstractCard> cardsToBag) {
        if (cardsToBag == null || cardsToBag.isEmpty()) return;
        addToBot(new MoveCardToBagAction(new ArrayList<>(cardsToBag)));
    }
}
