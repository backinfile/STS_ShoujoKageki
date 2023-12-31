package ShoujoKageki.actions.bag;

import ShoujoKageki.cards.patches.BagFieldPatch;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.character.BasePlayer;
import ShoujoKageki.powers.BagPower;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class TakeSpecCardFromBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToTake = new ArrayList<>();
    private final boolean discardOverflowedCard;

    public TakeSpecCardFromBagAction(ArrayList<AbstractCard> cardsToTake, boolean discardOverflowedCard) {
        this.discardOverflowedCard = discardOverflowedCard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardsToTake.addAll(cardsToTake);
    }

    public TakeSpecCardFromBagAction(AbstractCard cardToTake, boolean discardOverflowedCard) {
        this.discardOverflowedCard = discardOverflowedCard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardsToTake.add(cardToTake);
    }

    @Override
    public void update() {
        BasePlayer player = (BasePlayer) AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup bag = BagField.bag.get(player);
            int handSize = player.hand.size();
            int toTake = cardsToTake.size();
            int takeCnt = 0;
            for (int i = 0; i < toTake; i++) {
                AbstractCard curCard = cardsToTake.get(i);

                BagFieldPatch.triggerOnTakeFromBag(curCard);

                if (handSize + i >= BaseMod.MAX_HAND_SIZE) {
                    if (discardOverflowedCard) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(curCard, player.hb.cX, player.hb.cY));
                    } else {
                        break;
                    }
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(curCard, player.hb.cX, player.hb.cY));
                }
                bag.removeCard(curCard);
                takeCnt++;
            }
            addToTop(new ApplyBagPowerAction());
//            addToTop(new ReducePowerAction(player, player, BagPower.POWER_ID, takeCnt));
            isDone = true;
            return;
        }
        tickDuration();
    }
}
