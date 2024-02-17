package ShoujoKageki_Karen.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class DropFuelEffect extends AbstractGameEffect {
    private Vector2 position;
    private Vector2 velocity;
    private TextureAtlas.AtlasRegion img = null;
    private ArrayList<Vector2> prevPositions = new ArrayList();
    private static boolean flipper = true;

    private static final Color MAIN_COLOR = new Color(1f, 0.88f, 0.17f, 1f);

    public DropFuelEffect() {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.duration = 1.5F;
        if (flipper) {
            this.position = new Vector2(-100.0F * Settings.scale - (float) this.img.packedWidth / 2.0F, (float) Settings.HEIGHT / 2.0F - (float) this.img.packedHeight / 2.0F);
        } else {
            this.position = new Vector2(-50.0F * Settings.scale - (float) this.img.packedWidth / 2.0F, (float) Settings.HEIGHT / 2.0F - (float) this.img.packedHeight / 2.0F);
        }

        flipper = !flipper;
        this.velocity = new Vector2(3000.0F * Settings.scale, 0.0F);
        this.color = new Color(1.0F, 1.0F, 0.2F, 1.0F);
        this.scale = 3.0F * Settings.scale;
    }

    public void update() {
        if (this.duration == 1.5F) {
            CardCrawlGame.sound.playA("ATTACK_WHIFF_1", -0.6F);
            CardCrawlGame.sound.playA("ORB_LIGHTNING_CHANNEL", 0.6F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD.cpy(), true));
        }

//        if (this.position.x > (float) Settings.WIDTH * 0.55F && this.position.y > (float) Settings.HEIGHT / 2.0F - (float) this.img.packedHeight / 2.0F) {
//            this.velocity.y = 0.0F;
//            this.position.y = (float) Settings.HEIGHT / 2.0F - (float) this.img.packedHeight / 2.0F;
//            this.velocity.x = 3000.0F * Settings.scale;
//        } else if (this.position.x > (float) Settings.WIDTH * 0.5F) {
//            this.velocity.y = 6000.0F * Settings.scale;
//        } else if (this.position.x > (float) Settings.WIDTH * 0.4F) {
//            this.velocity.y = -6000.0F * Settings.scale;
//        } else if (this.position.x > (float) Settings.WIDTH * 0.35F) {
//            this.velocity.y = 6000.0F * Settings.scale;
//            this.velocity.x = 2000.0F * Settings.scale;
//        }

        this.prevPositions.add(this.position.cpy());
        this.position.mulAdd(this.velocity, Gdx.graphics.getDeltaTime());
        if (this.prevPositions.size() > 30) {
            this.prevPositions.remove(0);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);

        for (int i = 0; i < this.prevPositions.size(); ++i) {
            sb.setColor(new Color(1.0F, 0.9F, 0.3F, 1.0F));
            float var10004 = (float) this.img.packedWidth / 2.0F;
            float var10005 = (float) this.img.packedHeight / 2.0F;
            float var10006 = (float) this.img.packedWidth;
            sb.draw(this.img, ((Vector2) this.prevPositions.get(i)).x, ((Vector2) this.prevPositions.get(i)).y, var10004, var10005, var10006, (float) this.img.packedHeight, this.scale / 8.0F * ((float) i * 0.05F + 1.0F) * MathUtils.random(1.5F, 3.0F), this.scale / 8.0F * ((float) i * 0.05F + 1.0F) * MathUtils.random(0.5F, 2.0F), 0.0F);
        }

        sb.setColor(Color.RED);
        sb.draw(this.img, this.position.x, this.position.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 2.5F, this.scale * 2.5F, 0.0F);
        sb.setBlendFunction(770, 771);
        sb.setColor(MAIN_COLOR);
        sb.draw(this.img, this.position.x, this.position.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, 0.0F);
    }

    public void dispose() {
    }
}
