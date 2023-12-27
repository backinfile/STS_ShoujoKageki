package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.effects.MoveCardToBagEffect;
import ShoujoKageki.powers.BagPower;

import java.util.ArrayList;

public class MoveCardToBagAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToBag;

    public MoveCardToBagAction(ArrayList<AbstractCard> cardsToBag) {
        this.cardsToBag = new ArrayList<>();
        this.cardsToBag.addAll(cardsToBag);
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public MoveCardToBagAction(AbstractCard card) {
        this.cardsToBag = new ArrayList<>();
        cardsToBag.add(card);
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (cardsToBag.isEmpty()) {
                isDone = true;
                return;
            }

            AbstractPlayer player = AbstractDungeon.player;

            ArrayList<AbstractCard> cards = new ArrayList<>(cardsToBag);
            for (AbstractCard card : cards) {
                player.hand.group.remove(card);
                AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card));
            }
            BagField.bag.get(player).group.addAll(cards);
            addToTop(new ApplyPowerAction(player, player, new BagPower(cards.size())));
        }
        tickDuration();
    }
}
