package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

public class StageLightShapeMultiEffect extends AbstractGameEffect {
    private static ShapeRenderer shapeRenderer;
    private static final float MAX_HEIGHT = Math.max(Settings.HEIGHT, Settings.WIDTH);
    private final AbstractCreature creature;
    private final List<Float> degrees = new ArrayList<>();

    public StageLightShapeMultiEffect(AbstractCreature creature) {
        this.creature = creature;
        float degree = 0f;
        for (int i = 0; i < 3 + MathUtils.random(0, 1); i++) {
            degree += MathUtils.random(50, 120);
            degree %= 360;
            degrees.add(degree);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch sb) {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(sb.getProjectionMatrix());
        }
        sb.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color toColor = new Color(1f, 0.5f, 0.5f, 0.5f);

        float monsterX = creature.drawX;
        float monsterY = creature.drawY;
        for (float degree : degrees) {
            shapeRenderer.rect(monsterX, monsterY, 0f, 0f, creature.hb.width / 2f, (float) getBorderDistance(monsterX, monsterY, degree), 1f, 1f,
                    degree, toColor, toColor, Color.RED, Color.RED);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        sb.begin();
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

    }
}
