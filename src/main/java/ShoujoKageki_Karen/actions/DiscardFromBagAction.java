package ShoujoKageki_Karen.actions;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.ApplyBagPowerAction;
import ShoujoKageki_Karen.actions.bag.CheckBagEmptyAction;
import ShoujoKageki_Karen.cards.patches.BagFieldPatch;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class DiscardFromBagAction extends AbstractGameAction {

    private final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(KarenPath.makeID(DiscardFromBagAction.class.getSimpleName()));
    private final ArrayList<AbstractCard> discardCards = new ArrayList<>();
    private boolean discardAll = false;

    public DiscardFromBagAction(ArrayList<AbstractCard> discardCards) {
        this.discardCards.addAll(discardCards);
        this.startDuration = this.duration = Settings.ACTION_DUR_XFAST;
    }

    public DiscardFromBagAction() {
        this.discardAll = true;
        this.startDuration = this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (BagField.isInfinite()) {
//            addToBot(new TalkAction(p, uiStrings.TEXT[0]));
            isDone = true;
            return;
        }

        if (BagField.isChangeToDrawPile()) {
            if (p.drawPile.isEmpty()) {
                isDone = true;
                return;
            }
            if (discardAll) {
                while (!p.drawPile.isEmpty()) {
                    AbstractCard card = p.drawPile.getTopCard();
                    p.drawPile.moveToDiscardPile(card);
                    trigger(card);
                }
                isDone = true;
                return;
            }
            for (AbstractCard card : discardCards) {
                p.drawPile.moveToDiscardPile(card);
                trigger(card);
            }
            addToTop(new CheckBagEmptyAction());
            isDone = true;
            return;
        }

        if (discardAll) {
            CardGroup bag = BagField.getBag();
            if (bag.isEmpty())  {
                isDone = true;
                return;
            }
            for (AbstractCard card : bag.group) {
                p.drawPile.moveToDiscardPile(card);
                trigger(card);
            }
            bag.clear();
            addToBot(new ApplyBagPowerAction());
            addToTop(new CheckBagEmptyAction());
            isDone = true;
            return;
        }

        for (AbstractCard card : discardCards) {
            BagField.getBag().removeCard(card);
            p.drawPile.moveToDiscardPile(card);
            trigger(card);
        }
        addToTop(new CheckBagEmptyAction());
        isDone = true;
    }

    private void trigger(AbstractCard card) {
        BagFieldPatch.triggerOnTakeFromBag(card);
    }
}

