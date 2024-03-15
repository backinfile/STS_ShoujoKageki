package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.WindyParticleEffect;

import java.util.Random;

public class WhirlwindLongEffect extends AbstractGameEffect {
    private float timer;
    private boolean reverse;

    private final Random random = new Random();

    public WhirlwindLongEffect(Color setColor, boolean reverse) {
        this.timer = 0.0F;
        this.reverse = false;
        this.color = setColor.cpy();
        this.reverse = reverse;
    }

    public WhirlwindLongEffect() {
        this(new Color(0.9F, 0.9F, 1.0F, 1.0F), false);
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += (random.nextInt(3) + 2f) / 10f;
            AbstractDungeon.effectsQueue.add(new WindyParticleEffect(this.color, this.reverse));
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}