package ShoujoKageki.actions.bag;

import ShoujoKageki.cards.bag.Continue;
import ShoujoKageki.cards.patches.BagFieldPatch;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.character.BasePlayer;
import ShoujoKageki.effects.ShowBagCardAndAddToDiscardEffect;
import ShoujoKageki.effects.ShowBagCardToHandEffect;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
        AbstractPlayer player = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int handSize = player.hand.size();
            for (int i = 0; i < amount; i++) {
                AbstractCard curCard = new Continue();//AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                if (BagField.isChangeToDrawPile(false)) {
                    curCard.current_x = 0;
                    curCard.current_y = -200.0F * Settings.scale;
                } else {
                    curCard.current_x = player.hb.cX;
                    curCard.current_y = player.hb.cY;
                }

                if (asDrawnCards) {
                    DrawCardAction.drawnCards.add(curCard);
                }
                if (BagField.isCostZero()) {
                    curCard.setCostForTurn(0);
                }

                BagFieldPatch.triggerOnTakeFromBagToHand(curCard);

                if (handSize + i >= BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowBagCardAndAddToDiscardEffect(curCard));
                } else {
                    AbstractDungeon.effectList.add(new ShowBagCardToHandEffect(curCard));
                }
            }
            addToTop(new CheckBagEmptyAction());
            isDone = true;
            return;
        }
        tickDuration();
    }
}
