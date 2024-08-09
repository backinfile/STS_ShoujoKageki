package ShoujoKageki.actions;

import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKagekiCore.shine.DisposableVariable;
import ShoujoKagekiCore.shine.DisposableField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class StarAction extends AbstractGameAction {
    public StarAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {

        ArrayList<AbstractCard> allShineCards = getAllShineCardsWithoutBag();
        if (allShineCards.size() != 1) {
            isDone = true;
            return;
        }
        AbstractCard card = allShineCards.get(0);

        // remove from player
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.group.remove(card)) p.hand.refreshHandLayout();
        p.discardPile.group.remove(card);
        p.drawPile.group.remove(card);
        if (BagField.getBag().group.remove(card)) addToBot(new ApplyBagPowerAction());

        // dispose
        int totalDisposable = DisposableVariable.getValue(card);
        float centerX = (float) Settings.WIDTH / 2.0F;
        float centerY = (float) Settings.HEIGHT / 2.0F;
        DisposableField.disposeCard(card, centerX, centerY);
        addToTop(new WaitAction(Settings.ACTION_DUR_FAST));


        // play
        for (int i = 0; i < totalDisposable; i++) {
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 250.0F * Settings.scale * (i + 1);
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            tmp.current_x = centerX;
            tmp.current_y = centerY;
            tmp.purgeOnUse = true;
            tmp.applyPowers();
//            DisposableVariable.setBaseValue(tmp, totalDisposable - i);
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, card.energyOnUse, true, true), false);
        }
        isDone = true;
    }

    public static ArrayList<AbstractCard> getAllShineCardsWithoutBag() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> result = new ArrayList<>();

        ArrayList<CardGroup> cardPools = new ArrayList<>();
        cardPools.add(p.hand);
        cardPools.add(p.discardPile);
//        cardPools.add(p.drawPile);
//        CardGroup bag = BagField.getBag();
//        if (bag != null && !BagField.isInfinite(false)) {
//            cardPools.add(bag);
//        }
        if (!BagField.isChangeToDrawPile(false)) {
            cardPools.add(p.drawPile);
        }

        for (CardGroup pool : cardPools) {
            for (AbstractCard card : pool.group) {
                if (DisposableVariable.isDisposableCard(card)) {
                    result.add(card);
                }
            }
        }
        return result;
    }
}
