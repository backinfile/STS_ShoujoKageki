package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LightFlashPowerEffect extends AbstractGameEffect {
    public static final float EFFECT_DUR = 2.0F;
    private float scale;
    private TextureAtlas.AtlasRegion region48 = null;

    public LightFlashPowerEffect(AbstractPower power) {
        if (power.img == null) {
            this.region48 = power.region48;
        }

        this.duration = EFFECT_DUR;
        this.startingDuration = EFFECT_DUR;
        this.scale = Settings.scale;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.5F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > 0.5F) {
            this.scale = Interpolation.exp5Out.apply(1.5F * Settings.scale, Settings.scale, -(this.duration - EFFECT_DUR) / 1.5F);
        } else {
            this.color.a = Interpolation.fade.apply(0.5F, 0.0F, 1.0F - this.duration);
        }
        if (duration <= 1f) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb, float x, float y) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if (this.region48 != null) {
            sb.draw(this.region48, x - (float)this.region48.packedWidth / EFFECT_DUR, y - (float)this.region48.packedHeight / EFFECT_DUR, (float)this.region48.packedWidth / EFFECT_DUR, (float)this.region48.packedHeight / EFFECT_DUR, (float)this.region48.packedWidth, (float)this.region48.packedHeight, this.scale, this.scale, 0.0F);
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }

    public void render(SpriteBatch sb) {
    }
}
