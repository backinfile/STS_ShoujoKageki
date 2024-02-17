package ShoujoKageki_Karen.actions.bag;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PutDeckTopCardIntoBagAction extends AbstractGameAction {

    private AbstractCard card = null;

    public PutDeckTopCardIntoBagAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST * 2;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new PutDeckTopCardIntoBagAction());
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                this.card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.player.limbo.group.add(card);
//                card.exhaustOnUseOnce = this.exhaustCards;
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.xScale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
            }
        }

        tickDuration();
        if (isDone && card != null) {
            AbstractPlayer player = AbstractDungeon.player;
            player.limbo.group.remove(card);
            addToTop(new MoveCardToBagAction(card));
        }
    }
}
