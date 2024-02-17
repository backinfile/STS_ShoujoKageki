package ShoujoKageki_Karen.actions;

import ShoujoKageki_Karen.actions.bag.MoveCardToBagAction;
import ShoujoKageki.util.Utils2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CopyAllHandToBagAction extends AbstractGameAction {
    public CopyAllHandToBagAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hand.isEmpty()) {
                isDone = true;
                return;
            }

            ArrayList<AbstractCard> copyCards = new ArrayList<>();
            for (AbstractCard card : p.hand.group) {
                AbstractCard copy = Utils2.makeCardCopyOnlyWithUpgrade(card);
                copy.current_x = card.current_x;
                copy.current_y = card.current_y;
                copy.angle = card.angle;
                copy.drawScale = card.drawScale;
                card.superFlash();

                copy.target_x = card.current_x;
                copy.target_y = card.target_y + AbstractCard.IMG_HEIGHT * card.drawScale * Settings.scale * 1f;
                copy.targetAngle = card.angle;
                copy.targetDrawScale = card.drawScale;

                p.limbo.addToTop(copy);
                copyCards.add(copy);
            }
            addToBot(new MoveCardToBagAction(copyCards));
            for (AbstractCard card : copyCards) {
                addToBot(new UnlimboAction(card));
            }
        }

        tickDuration();
    }
}
