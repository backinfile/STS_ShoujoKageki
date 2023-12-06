package thief.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thief.cards.tool.patch.BagField;
import thief.effects.MoveCardToBagEffect;
import thief.powers.BagPower;

import java.util.ArrayList;
import java.util.List;

import static thief.ModInfo.makeID;

public class BagAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(BagAction.class.getName());

    private static final String ID = makeID(BagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiString.TEXT;

    private final AbstractPlayer player;
    private boolean allCardInHand = false;
    private boolean replaceAllCardInHand = false;

    public BagAction(AbstractPlayer player, int amount) {
        this.player = player;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    // all card in hand
    public BagAction(AbstractPlayer player, boolean allCardInHand, boolean replaceAllCardInHand) {
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
                return;
            }
            if (allCardInHand) {
                bagCards(player.hand.group);
                isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
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
        CardGroup bag = BagField.bag.get(player);
        int beforeAmount = bag.size();

        ArrayList<AbstractCard> bagCards = new ArrayList<>(bag.group);
        bag.clear();
        if (beforeAmount > 0) {
            addToBot(new ReducePowerAction(player, player, BagPower.POWER_ID, beforeAmount));
        }
        addToBot(new BagAction(player, true, false));
        addToBot(new PutCardsToHandAction(player, bagCards));
    }

    private void bagCards(List<AbstractCard> cardsToBag) {
        if (cardsToBag == null || cardsToBag.isEmpty()) return;
        ArrayList<AbstractCard> cards = new ArrayList<>(cardsToBag);
        for (AbstractCard card : cards) {
            player.hand.removeCard(card);
            AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
        }
        addToTop(new WaitAction(MoveCardToBagEffect.DURATION));
        BagField.bag.get(player).group.addAll(cards);
        addToBot(new ApplyPowerAction(player, player, new BagPower(cards.size())));
    }
}
