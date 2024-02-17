package ShoujoKageki_Karen.actions.bag;

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

import java.util.ArrayList;
import java.util.List;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class PutHandCardIntoBagAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(PutHandCardIntoBagAction.class.getName());

    private static final String ID = makeID(PutHandCardIntoBagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiString.TEXT;

    private final AbstractPlayer player;
    private boolean allCardInHand = false;

    public PutHandCardIntoBagAction(int amount) {
        this.player = AbstractDungeon.player;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    // all card in hand
    public PutHandCardIntoBagAction(boolean allCardInHand) {
        this(1);
        this.allCardInHand = allCardInHand;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
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


    public static void bagCards(List<AbstractCard> cardsToBag) {
        if (cardsToBag == null || cardsToBag.isEmpty()) return;
        AbstractDungeon.actionManager.addToBottom(new MoveCardToBagAction(new ArrayList<>(cardsToBag)));
    }
}
