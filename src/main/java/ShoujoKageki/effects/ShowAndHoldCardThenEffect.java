package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import ShoujoKageki.util.ActionUtils;

public class ShowAndHoldCardThenEffect extends AbstractGameEffect {
    public static final float DURATION = Settings.ACTION_DUR_XLONG;
    private final AbstractCard card;
    private final AbstractGameEffect nextEffect;

    public ShowAndHoldCardThenEffect(AbstractCard card, boolean justCreate, boolean randomCenter, AbstractGameEffect nextEffect) {
        this.card = card;
        this.nextEffect = nextEffect;


        ActionUtils.resetBeforeMoving(card);
        this.card.setAngle(0.0F);
        this.card.targetDrawScale = 0.75F;
        card.targetTransparency = 1.0F;
        card.fadingOut = false;

        if (randomCenter) {
            this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F);
            this.card.target_y = MathUtils.random((float) Settings.HEIGHT * 0.3F, (float) Settings.HEIGHT * 0.7F);
        } else {
            this.card.target_x = Settings.WIDTH * 0.5F;
            this.card.target_y = Settings.HEIGHT * 0.5F;
        }
        if (justCreate) {
            this.card.drawScale = 0.1f;
            this.card.current_x = this.card.target_x;
            this.card.current_y = this.card.target_y;
        }

        duration = startingDuration = DURATION;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            card.render(sb);
        }
    }

    @Override
    public void dispose() {
        if (nextEffect != null) {
            AbstractDungeon.effectList.add(nextEffect);
        }
    }
}
