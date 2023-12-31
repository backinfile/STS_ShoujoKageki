package ShoujoKageki.actions;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Set;

public class DiscardFromBagAction extends AbstractGameAction {

    private final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(DiscardFromBagAction.class.getSimpleName()));
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
            addToBot(new TalkAction(p, uiStrings.TEXT[0]));
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
            isDone = true;
            return;
        }

        if (discardAll) {
            CardGroup bag = BagField.getBag();
            for (AbstractCard card : bag.group) {
                p.drawPile.moveToDiscardPile(card);
                trigger(card);
            }
            bag.clear();
            addToBot(new ApplyBagPowerAction());
            isDone = true;
            return;
        }

        for (AbstractCard card : discardCards) {
            BagField.getBag().removeCard(card);
            p.drawPile.moveToDiscardPile(card);
            trigger(card);
        }
        isDone = true;
    }

    private void trigger(AbstractCard card) {
        if (card instanceof BaseCard) ((BaseCard) card).triggerOnTakeFromBag();
    }
}

