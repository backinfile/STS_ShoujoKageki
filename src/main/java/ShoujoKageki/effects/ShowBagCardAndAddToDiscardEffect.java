package ShoujoKageki.effects;

import ShoujoKageki.cards.patches.field.BagField;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import java.util.Iterator;

public class ShowBagCardAndAddToDiscardEffect extends AbstractGameEffect {
    private AbstractCard card;

    public ShowBagCardAndAddToDiscardEffect(AbstractCard srcCard) {
        this.card = srcCard;
        AbstractPlayer player = AbstractDungeon.player;

        if (BagField.isChangeToDrawPile(false)) {
            card.current_x = AbstractDungeon.overlayMenu.combatDeckPanel.show_x;
            card.current_y = AbstractDungeon.overlayMenu.combatDeckPanel.show_y;
        } else {
            card.current_x = player.hb.cX;
            card.current_y = player.hb.cY;
        }
        card.drawScale = 0.1f;
        player.discardPile.moveToDiscardPile(srcCard);
        this.duration = 0;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.getCurrRoom().souls.discard(this.card, true);
        }

    }

    public void render(SpriteBatch sb) {
//        if (!this.isDone) {
//            this.card.render(sb);
//        }
    }

    public void dispose() {
    }
}