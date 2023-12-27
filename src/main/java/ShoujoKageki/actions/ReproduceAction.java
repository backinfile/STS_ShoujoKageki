package ShoujoKageki.actions;

import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.powers.BagPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReproduceAction extends AbstractGameAction {
    public ReproduceAction(int amount) {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {

            CardGroup bag = BagField.bag.get(AbstractDungeon.player);
            int size = bag.size();
            for (int i = 0; i < size; i++) {
                AbstractCard card = bag.group.get(i);
                addToBot(new ExhaustSpecificCardAction(card, bag));
            }
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, BagPower.POWER_ID));

            for (int i = 0; i < size * amount; i++) {
                AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                addToBot(new MakeTempCardInBagAction(card, 1, true, true));
            }
            isDone = true;
        }
    }
}
