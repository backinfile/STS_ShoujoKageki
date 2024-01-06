package ShoujoKageki.effects;

import ShoujoKageki.cards.patches.field.BagField;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShowBagCardToHandEffect extends AbstractGameEffect {
    private final AbstractCard card;

    public ShowBagCardToHandEffect(AbstractCard card) {
        this.card = card;
        UnlockTracker.markCardAsSeen(card.cardID);

        AbstractPlayer player = AbstractDungeon.player;
        if (BagField.isChangeToDrawPile(false)) {
            card.current_x = AbstractDungeon.overlayMenu.combatDeckPanel.show_x;
            card.current_y = AbstractDungeon.overlayMenu.combatDeckPanel.show_y;
        } else {
            card.current_x = player.hb.cX;
            card.current_y = player.hb.cY;
        }
        card.drawScale = 0.1F;

//        card.target_x = offsetX;
//        card.target_y = offsetY;
        this.duration = 0.8F;

        card.targetDrawScale = 0.75F;
        card.transparency = 0.01F;
        card.targetTransparency = 1.0F;
        card.fadingOut = false;
//        this.playCardObtainSfx();
        if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            card.upgrade();
        }

        player.hand.addToHand(card);
//        card.triggerWhenCopied();
        player.hand.refreshHandLayout();
        player.hand.applyPowers();
        player.onCardDrawOrDiscard();
        if (player.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL) {
            card.setCostForTurn(-9);
        }
    }


    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }
}