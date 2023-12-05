package thief.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thief.effects.MoveCardToBagEffect;
import thief.powers.BagPower;

import static thief.ModInfo.makeID;

public class BagAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(BagAction.class.getName());

    private static final String ID = makeID(BagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiString.TEXT;

    public BagAction(int amount) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (duration == startDuration) {
            if (player.hand.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            CardGroup selectedCards = AbstractDungeon.handCardSelectScreen.selectedCards;
            if (selectedCards.isEmpty()) {
                isDone = true;
                return;
            }

            for (AbstractCard card : selectedCards.group) {
                player.hand.removeCard(card);
                AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
            }
            addToTop(new WaitAction(MoveCardToBagEffect.DURATION));

            BagPower oldPower = (BagPower) player.getPower(BagPower.POWER_ID);
            if (oldPower != null) {
                oldPower.addBagCards(selectedCards.group);
                oldPower.stackPower(selectedCards.size());
            } else {
                addToBot(new ApplyPowerAction(player, player, new BagPower(selectedCards.group)));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }
}
