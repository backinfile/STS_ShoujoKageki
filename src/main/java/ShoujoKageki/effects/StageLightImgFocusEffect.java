package ShoujoKageki.effects;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.patches.StageLightPatch;
import ShoujoKageki.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

public class StageLightImgFocusEffect extends AbstractGameEffect {
    private final AbstractCreature creature;

    private static final Texture IMG = TextureLoader.getTexture(ModInfo.makeUIPath("stage_light2.png"));
    private final BlackScreenEffect blackScreenEffect = new BlackScreenEffect(0.5f);

    public StageLightImgFocusEffect(AbstractCreature creature) {
        this.creature = creature;
        AbstractDungeon.effectList.add(blackScreenEffect);
    }

    @Override
    public void update() {
        if (!StageLightPatch.inLight) {
            isDone = true;
            blackScreenEffect.hide();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        float scaleX = Settings.scale * 0.3f;
        float scaleY = Settings.scale * 1.2f;

        float monsterX = creature.hb.cX;
        float monsterY = creature.hb.cY;
        sb.setColor(Color.WHITE);

        float degree = 0;
        float targetWidth = (creature.hb.width + creature.hb.height) * 0.2f;;//IMG.getWidth() * scaleX;
        float targetHeight = IMG.getHeight() * scaleY * 0.8f;

        float distance = targetWidth * 0.5f; //creature.hb.height / 2f / MathUtils.sinDeg(degree + 90);
        float fromX = monsterX;
        float fromY = monsterY;// + creature.hb.height / 2f + 10f * Settings.scale;

        sb.draw(IMG, fromX - targetWidth / 2f, fromY, targetWidth / 2f, 0f, targetWidth, targetHeight,
                1f, 1f, degree, 0, 0, IMG.getWidth(), IMG.getHeight(), false, false);
    }

    public static double getBorderDistance(float x, float y, float degree) {
        int w = Settings.WIDTH;
        int h = Settings.HEIGHT;
        if (degree < 90) {
            return Math.sqrt(x * x + (y - h) * (y - h));
        } else if (degree < 180) {
            return Math.sqrt(x * x + y * y);
        } else if (degree < 270) {
            return Math.sqrt((w - x) * (w - x) + y * y);
        } else {
            return Math.sqrt((w - x) * (w - x) + (y - h) * (y - h));
        }
    }

    @Override
    public void dispose() {
        blackScreenEffect.hide();
    }
}
