package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ShoujoKageki.util.ActionUtils;

public class MoveCardToBagEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MoveCardToBagEffect.class.getName());
    public AbstractCard card;
    private boolean justStart = true;

    public static float DURATION = Settings.ACTION_DUR_FAST;

    public MoveCardToBagEffect(AbstractCard card) {
        super();
        this.card = card;
        ActionUtils.resetBeforeMoving(card);
        duration = startingDuration = DURATION;
    }

    @Override
    public void update() {
        if (justStart) {
            justStart = false;
            card.untip();
            card.unhover();
            card.setAngle(0.0F);
            card.targetDrawScale = 0.001F;
            card.target_x = AbstractDungeon.player.hb.cX;
            card.target_y = AbstractDungeon.player.hb.cY;
//            AbstractDungeon.player.hand.refreshHandLayout();
//            AbstractDungeon.player.hand.applyPowers();
        }

        card.update();

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            card.render(sb);
        }

    }

    @Override
    public void dispose() {
    }
}
