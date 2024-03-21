package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BlackScreenEffect extends AbstractGameEffect {
    private Color blackScreenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    private float blackScreenTarget = 0.001f;

    public BlackScreenEffect(float blackScreenTarget) {
        this.blackScreenTarget = blackScreenTarget;
    }

    @Override
    public void update() {
        if (this.blackScreenColor.a != this.blackScreenTarget) {
            Color var10000;
            if (this.blackScreenTarget > this.blackScreenColor.a) {
                var10000 = this.blackScreenColor;
                var10000.a += Gdx.graphics.getDeltaTime() * 2.0F;
                if (this.blackScreenColor.a > this.blackScreenTarget) {
                    this.blackScreenColor.a = this.blackScreenTarget;
                }
            } else {
                var10000 = this.blackScreenColor;
                var10000.a -= Gdx.graphics.getDeltaTime() * 2.0F;
                if (this.blackScreenColor.a < this.blackScreenTarget) {
                    this.blackScreenColor.a = this.blackScreenTarget;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.blackScreenColor.a != 0.0F) {
            sb.setColor(this.blackScreenColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        } else {
            isDone = true;
        }
    }

    public void hide() {
        blackScreenTarget = 0f;
    }

    @Override
    public void dispose() {

    }
}
