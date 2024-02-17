package ShoujoKageki.actions.bag;

import ShoujoKageki.karen.cards.bag.Continue;
import ShoujoKageki.karen.cards.patches.BagFieldPatch;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayBagTopCardAction extends AbstractGameAction {
    private final boolean exhaustCards;

    public PlayBagTopCardAction(AbstractCreature target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;

            if (BagField.isInfinite()) {
                Continue card = new Continue();
                if (BagField.isChangeToDrawPile(false)) {
                    card.current_x = 0;
                    card.current_y = -200.0F * Settings.scale;
                } else {
                    card.current_x = p.hb.cX;
                    card.current_y = p.hb.cY;
                }
                play(card);
                isDone = true;
                return;
            }

            if (BagField.isChangeToDrawPile()) {
                if (!p.drawPile.isEmpty()) {
                    addToTop(new PlayTopCardAction(target, exhaustCards));
                }
                isDone = true;
                return;
            }

            CardGroup bag = BagField.getBag();
            if (bag.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractCard card = bag.getBottomCard();
            bag.removeCard(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);
            BagFieldPatch.triggerOnTakeFromBagToHand(card);
            addToBot(new ApplyBagPowerAction());
            addToBot(new CheckBagEmptyAction());


            card.current_x = p.hb.cX;
            card.current_y = p.hb.cY;
            play(card);
            this.isDone = true;
        }
    }

    private void play(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        card.exhaustOnUseOnce = this.exhaustCards;
        p.limbo.group.add(card);

        card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = (float) Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        this.addToTop(new NewQueueCardAction(card, this.target, false, true));
        this.addToTop(new UnlimboAction(card));
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
    }
}