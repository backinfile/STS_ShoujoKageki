package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class PutCardsToHandAction extends AbstractGameAction {

    private final ArrayList<AbstractCard> cards;
    private final AbstractPlayer player;

    public PutCardsToHandAction(AbstractPlayer player, ArrayList<AbstractCard> cards) {
        this.player = player;
        this.cards = cards;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            int handSize = player.hand.size();
            for (int i = 0; i < cards.size(); i++) {
                AbstractCard card = cards.get(i);
                if (i + handSize < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, player.hb.cX, player.hb.cY));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, player.hb.cX, player.hb.cY));
                }
            }
        }
        tickDuration();
    }
}
