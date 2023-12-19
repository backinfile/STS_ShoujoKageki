package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import ShoujoKageki.cards.patches.BagField;
import ShoujoKageki.character.BasePlayer;
import ShoujoKageki.powers.BagPower;
import ShoujoKageki.relics.BagDiscoverRelic;

import java.util.ArrayList;

import static ShoujoKageki.ModInfo.makeID;

public class TakeCardFromBagAction extends AbstractGameAction {
    private static final String ID = makeID(TakeCardFromBagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private final boolean rnd;
    private boolean retrieveCard = false;

    public TakeCardFromBagAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
        this.rnd = false;
    }

    public TakeCardFromBagAction(int amount, boolean rnd) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.rnd = rnd;
    }

    @Override
    public void update() {
        BasePlayer player = (BasePlayer) AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (player.hand.size() >= 10) {
                isDone = true;
                return;
            }
            CardGroup bag = BagField.bag.get(player);

            if (rnd) { // 随机获取
                bag.shuffle();
                int toTake = Math.min(amount, bag.size());
                for (int i = 0; i < toTake; i++) {
                    AbstractCard topCard = bag.getTopCard();
                    bag.removeCard(topCard);
                    if (AbstractDungeon.player.hand.size() + i < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(topCard, player.hb.cX, player.hb.cY));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(topCard, player.hb.cX, player.hb.cY));
                    }
                }
                addToTop(new ReducePowerAction(player, player, BagPower.POWER_ID, toTake));
                isDone = true;
                return;
            }

            if (amount > 1) {
                for (int i = 0; i < amount; amount++) {
                    this.addToTop(new TakeCardFromBagAction());
                }
                isDone = true;
                return;
            }


            if (bag.isEmpty()) return;
            ArrayList<AbstractCard> cards = rnd3Cards(bag);

            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, uiString.TEXT[0], false);
        } else if (!retrieveCard) {
            AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard;
            if (card != null) {  // 等待选择卡牌
                BagField.bag.get(player).removeCard(card);
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
                retrieveCard = true;
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
                addToTop(new ReducePowerAction(player, player, BagPower.POWER_ID, 1));
            }
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
