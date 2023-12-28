package ShoujoKageki.actions.bag;

import ShoujoKageki.cards.bag.Continue;
import ShoujoKageki.cards.patches.BagFieldPatch;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.character.BasePlayer;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class TakeRndTmpCardFromBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToTake = new ArrayList<>();
    private final boolean asDrawnCards;

    public TakeRndTmpCardFromBagAction(int amount) {
        this(amount, false);
    }

    public TakeRndTmpCardFromBagAction(int amount, boolean asDrawnCards) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.asDrawnCards = asDrawnCards;
    }

    @Override
    public void update() {
        BasePlayer player = (BasePlayer) AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int handSize = player.hand.size();
            for (int i = 0; i < amount; i++) {
                AbstractCard curCard = new Continue();//AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();

                if (asDrawnCards) {
                    DrawCardAction.drawnCards.add(curCard);
                }
                if (BagField.isCostZero()) {
                    curCard.setCostForTurn(0);
                }

                BagFieldPatch.triggerOnTakeFromBag(curCard);

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
