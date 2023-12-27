package ShoujoKageki.actions;

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

public class TakeRndTmpCardFromBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToTake = new ArrayList<>();

    public TakeRndTmpCardFromBagAction(int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    @Override
    public void update() {
        BasePlayer player = (BasePlayer) AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int handSize = player.hand.size();
            for (int i = 0; i < amount; i++) {
                AbstractCard curCard = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                if (BagField.isCostZero()) {
                    curCard.setCostForTurn(0);
                }
                if (handSize + i >= BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(curCard, player.hb.cX, player.hb.cY));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(curCard, player.hb.cX, player.hb.cY));
                }
            }
            isDone = true;
            return;
        }
        tickDuration();
    }
}
