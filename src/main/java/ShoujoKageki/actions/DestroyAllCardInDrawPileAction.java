package ShoujoKageki.actions;

import ShoujoKageki.effects.ShowAndHoldCardThenEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DestroyAllCardInDrawPileAction extends AbstractGameAction {
    public DestroyAllCardInDrawPileAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            CardGroup drawPile = AbstractDungeon.player.drawPile;
            for (int i = 0; i < drawPile.size(); i++) {
                AbstractCard card = drawPile.group.get(i);
//                AbstractDungeon.effectList.add(new ShowAndHoldCardThenEffect(card));
                addToTop(new ExhaustSpecificCardAction(card, drawPile));
            }
            isDone = true;
        }
    }
}
