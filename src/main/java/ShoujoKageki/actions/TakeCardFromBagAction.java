package ShoujoKageki.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.character.BasePlayer;
import ShoujoKageki.relics.BagDiscoverRelic;

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
            if (player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                isDone = true;
                return;
            }
            CardGroup bag = BagField.bag.get(player);

            if (rnd) { // 随机获取
                ArrayList<AbstractCard> toTakeCards = new ArrayList<>();
                for (int i = 0; i < amount && !bag.isEmpty(); i++) {
                    int rnd = AbstractDungeon.cardRng.random(bag.size() - 1);
                    AbstractCard card = bag.group.get(rnd);
                    toTakeCards.add(card);
                    bag.removeCard(card);
                }
                addToTop(new TakeSpecCardFromBagAction(toTakeCards, discardOverflowedCard));
            } else {
                ArrayList<AbstractCard> toTakeCards = new ArrayList<>();
                for (int i = 0; i < amount && !bag.isEmpty(); i++) {
                    AbstractCard card = bag.getBottomCard();
                    toTakeCards.add(card);
                    bag.removeCard(card);
                }
                addToTop(new TakeSpecCardFromBagAction(toTakeCards, discardOverflowedCard));
            }
            isDone = true;
            return;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> rnd3Cards(CardGroup cardGroup) {
        int takeNum = 3;
        if (AbstractDungeon.player.hasRelic(BagDiscoverRelic.ID)) takeNum = BagDiscoverRelic.TAKE_NUM;
        ArrayList<AbstractCard> cards = new ArrayList<>();
        if (cardGroup.size() <= takeNum) {
            cards.addAll(cardGroup.group);
        } else {
            cardGroup.shuffle();
            for (int i = 0; i < takeNum; i++) {
                cards.add(cardGroup.getNCardFromTop(i));
            }
        }
        return cards;
    }
}
