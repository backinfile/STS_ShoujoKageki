package thief.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import thief.powers.BagPower;

import java.util.ArrayList;
import java.util.List;

import static thief.ModInfo.makeID;

public class TakeCardFromBagAction extends AbstractGameAction {
    private static final String ID = makeID(TakeCardFromBagAction.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);
    private boolean retrieveCard = false;

    public TakeCardFromBagAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hand.size() >= 10) {
                isDone = true;
                return;
            }

            BagPower power = (BagPower) player.getPower(BagPower.POWER_ID);
            if (power == null || power.bag.isEmpty()) {
                isDone = true;
                return;
            }
            ArrayList<AbstractCard> cards = rnd3Cards(power.bag);

            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, uiString.TEXT[0], false);
        } else if (!retrieveCard) {
            AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard;
            if (card != null) {
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
                retrieveCard = true;
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> rnd3Cards(CardGroup cardGroup) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        if (cardGroup.size() <= 3) {
            cards.addAll(cardGroup.group);
        } else {
            CardGroup copy = new CardGroup(cardGroup, CardGroup.CardGroupType.UNSPECIFIED);
            for (int i = 0; i < 3; i++) {
                AbstractCard take = copy.getRandomCard(true);
                copy.removeCard(take);
                cards.add(take);
            }
        }
        return cards;
    }
}
