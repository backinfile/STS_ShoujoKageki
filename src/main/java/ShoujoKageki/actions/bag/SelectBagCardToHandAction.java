package ShoujoKageki.actions.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SelectBagCardToHandAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(SelectBagCardToHandAction.class.getSimpleName())).TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;
    private int newCost;
    private boolean setCost;

    public SelectBagCardToHandAction(int numberOfCards, boolean optional) {
        this.newCost = 0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
        this.setCost = false;
    }

    public SelectBagCardToHandAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public SelectBagCardToHandAction(int numberOfCards, int newCost) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = false;
        this.setCost = true;
        this.newCost = newCost;
    }

    public void update() {
        CardGroup bag = BagField.getBag();

        if (this.duration == this.startDuration) {
            if (BagField.isInfinite()) {
                addToTop(new TakeRndTmpCardFromBagAction(numberOfCards));
                isDone = true;
                return;
            }

            if (BagField.isChangeToDrawPile()) {
                addToBot(new BetterDrawPileToHandAction(numberOfCards));
                isDone = true;
                return;
            }

            if (bag.isEmpty() || this.numberOfCards <= 0) {
                isDone = true;
                return;
            }
            if (bag.size() <= this.numberOfCards && !this.optional) {
                addToTop(new TakeCardFromBagAction());
                this.isDone = true;
                return;
            }
            if (this.numberOfCards == 1) {
                if (this.optional) {
                    AbstractDungeon.gridSelectScreen.open(bag, this.numberOfCards, true, TEXT[0]);
                } else {
                    AbstractDungeon.gridSelectScreen.open(bag, this.numberOfCards, TEXT[0], false);
                }
            } else if (this.optional) {
                AbstractDungeon.gridSelectScreen.open(bag, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
            } else {
                AbstractDungeon.gridSelectScreen.open(bag, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
            }
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // selectFinish
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (this.setCost) {
                    c.setCostForTurn(this.newCost);
                }
//                c.lighten(false);
//                c.unhover();
//                c.applyPowers();
            }
            addToTop(new TakeSpecCardFromBagAction(AbstractDungeon.gridSelectScreen.selectedCards, true));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
//            AbstractDungeon.player.hand.refreshHandLayout();
            isDone = true;
        }
    }

}