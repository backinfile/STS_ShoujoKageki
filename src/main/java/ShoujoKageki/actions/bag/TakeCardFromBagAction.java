package ShoujoKageki.actions.bag;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.character.BasePlayer;

import java.util.ArrayList;

import static ShoujoKageki.ModInfo.makeID;

public class TakeCardFromBagAction extends AbstractGameAction {
    private static final String ID = makeID(TakeCardFromBagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private final boolean rnd;
    private final boolean discardOverflowedCard;

    public TakeCardFromBagAction() {
        this(999, false, false);
    }

    public TakeCardFromBagAction(int amount) {
        this(amount, false, false);
    }

    public TakeCardFromBagAction(int amount, boolean rnd, boolean discardOverflowedCard) {
        this.discardOverflowedCard = discardOverflowedCard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.rnd = rnd;
    }

    @Override
    public void update() {
        BasePlayer player = (BasePlayer) AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int handSize = player.hand.size();
            if (handSize >= BaseMod.MAX_HAND_SIZE) {
                isDone = true;
                return;
            }
            if (BagField.isChangeToDrawPile()) {
                if (discardOverflowedCard) {
                    addToTop(new DrawCardAction(amount));
                } else {
                    int toDraw = Math.min(amount, BaseMod.MAX_HAND_SIZE - handSize);
                    addToTop(new DrawCardAction(toDraw));
                }
                isDone = true;
                return;
            }

            CardGroup bag = BagField.bag.get(player);

            if (BagField.isInfinite()) {
                int toTake = Math.min(BaseMod.MAX_HAND_SIZE - handSize, amount);
                addToTop(new TakeRndTmpCardFromBagAction(toTake));
                isDone = true;
                return;
            }

            if (rnd) { // 随机获取
                ArrayList<AbstractCard> toTakeCards = new ArrayList<>();
                for (int i = 0; i < amount && !bag.isEmpty(); i++) {
                    if (!discardOverflowedCard && handSize + i >= BaseMod.MAX_HAND_SIZE) break;
                    int rnd = AbstractDungeon.cardRng.random(bag.size() - 1);
                    AbstractCard card = bag.group.get(rnd);
                    toTakeCards.add(card);
                    bag.removeCard(card);
                }
                addToTop(new TakeSpecCardFromBagAction(toTakeCards, true));
            } else {
                ArrayList<AbstractCard> toTakeCards = new ArrayList<>();
                for (int i = 0; i < amount && !bag.isEmpty(); i++) {
                    if (!discardOverflowedCard && handSize + i >= BaseMod.MAX_HAND_SIZE) break;
                    AbstractCard card = bag.getBottomCard();
                    toTakeCards.add(card);
                    bag.removeCard(card);
                }
                addToTop(new TakeSpecCardFromBagAction(toTakeCards, true));
            }
            isDone = true;
            return;
        }
        tickDuration();
    }
}
