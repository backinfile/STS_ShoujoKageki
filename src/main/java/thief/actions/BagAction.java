package thief.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

    public BagAction(AbstractPlayer player, int amount) {
        this.player = player;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    // all card in hand
    public BagAction(AbstractPlayer player) {
        this(player, 0);
        allCardInHand = true;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (player.hand.isEmpty()) {
                isDone = true;
                return;
            }
            if (allCardInHand) {
                bagCards(player, player.hand.group);
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
            bagCards(player, selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }

    private void bagCards(AbstractPlayer player, List<AbstractCard> cardsToBag) {
        if (cardsToBag == null || cardsToBag.isEmpty()) return;
        ArrayList<AbstractCard> cards = new ArrayList<>(cardsToBag);
        for (AbstractCard card : cards) {
            player.hand.removeCard(card);
            AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
        }
        addToTop(new WaitAction(MoveCardToBagEffect.DURATION));

        BagPower oldPower = (BagPower) player.getPower(BagPower.POWER_ID);
        if (oldPower != null) {
            oldPower.addBagCards(cards);
            oldPower.stackPower(cards.size());
        } else {
            addToBot(new ApplyPowerAction(player, player, new BagPower(cards)));
        }
    }
}
