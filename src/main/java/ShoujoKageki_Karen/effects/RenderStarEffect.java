package ShoujoKageki_Karen.effects;

import ShoujoKageki_Karen.KarenPath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RenderStarEffect extends AbstractGameEffect {
    private static final Texture Star = new Texture(Gdx.files.internal(KarenPath.makeUIPath("Star.png")));

    public final AbstractCard card;
    private float scale = 1f;

    public RenderStarEffect(AbstractCard card) {
        this.card = card;
        this.duration = this.startingDuration = Settings.ACTION_DUR_LONG;
        this.color = Color.WHITE.cpy();
    }

    @Override
    public void render(SpriteBatch sb) {
        float width = Star.getWidth() * 0.12f;
        float height = Star.getHeight() * 0.12f;

        this.scale = MathUtils.lerp(0f, 1f, duration / Settings.ACTION_DUR_XFAST);

        sb.setColor(Color.WHITE);
        sb.draw(Star, this.card.current_x - width * scale / 2f,
                this.card.current_y - height * scale / 2f,
                width * scale,
                height * scale
        );
    }

    @Override
    public void update() {
        super.update();

        if (isDone) {
            AbstractDungeon.effectsQueue.add(new MoveCardToBagEffect(card, true));
        }
    }

    @Override
    public void dispose() {
    }
}
