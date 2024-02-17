package ShoujoKageki.actions.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SelectDrawPileToBagAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(SelectDiscardCardIntoBagAction.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;

    private boolean reduceCost;

    public SelectDrawPileToBagAction(int amount, boolean reduceCost) {
        this.reduceCost = reduceCost;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }

        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == this.startDuration) {
            if (BagField.isChangeToDrawPile()) {
                isDone = true;
                return;
            }


            if (p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(p.drawPile, Math.min(this.amount, p.drawPile.size()), TEXT[0], false, false, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (reduceCost) c.setCostForTurn(0);
                p.drawPile.removeCard(c);
            }
            addToBot(new MoveCardToBagAction(AbstractDungeon.gridSelectScreen.selectedCards));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
            isDone = true;
        }

    }
}