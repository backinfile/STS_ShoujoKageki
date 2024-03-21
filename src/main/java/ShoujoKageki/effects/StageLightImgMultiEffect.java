package ShoujoKageki.effects;

import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

public class StageLightImgMultiEffect extends AbstractGameEffect {
    private final AbstractCreature creature;
    private final List<Float> degrees = new ArrayList<>();
    private final List<Float> waitDegrees = new ArrayList<>();

    private float addDegreeTimer = 0f;
    public static final float STAY_DURATION = 0.6f;
    public static final float ANI_DURATION = Settings.ACTION_DUR_FAST;

    private static final Texture IMG = TextureLoader.getTexture(ModInfo.makeUIPath("stage_light.png"));

    public StageLightImgMultiEffect(AbstractCreature creature) {
        this.creature = creature;
        this.color = Color.WHITE.cpy();
        this.duration = this.startingDuration = STAY_DURATION;
        waitDegrees.add(70f);
        waitDegrees.add(35f);
        waitDegrees.add(270f + 80f);
        waitDegrees.add(270f + 40f);
        CardCrawlGame.sound.play(AudioPatch.Sound_Stage_Light);
    }

    @Override
    public void update() {
        super.update();

        if (addDegreeTimer <= 0 && !waitDegrees.isEmpty()) {
            addDegreeTimer = 0.06f;
            degrees.add(waitDegrees.remove(0));
        }
        addDegreeTimer -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb) {
        float scaleX = Settings.scale * 0.3f;
        float scaleY = Settings.scale * 1.2f;

        float monsterX = creature.hb.cX;
        float monsterY = creature.hb.cY;
        sb.setColor(Color.WHITE);
        for (float degree : degrees) {
            float targetWidth = (creature.hb.width + creature.hb.height) * 0.2f;//IMG.getWidth() * scaleX;
            float targetHeight = IMG.getHeight() * scaleY;

            float distance = targetWidth * 0.5f; //creature.hb.height / 2f / MathUtils.sinDeg(degree + 90);
            float fromX = monsterX - MathUtils.cosDeg(degree + 90) * distance;
            float fromY = monsterY - MathUtils.sinDeg(degree + 90) * distance;

            sb.draw(IMG, fromX - targetWidth / 2f, fromY, targetWidth / 2f, 0f, targetWidth, targetHeight,
                    1f, 1f, degree, 0, 0, IMG.getWidth(), IMG.getHeight(), false, false);
        }
    }

    @Override
    public void dispose() {
        AbstractDungeon.overlayMenu.hideBlackScreen();
    }
}
