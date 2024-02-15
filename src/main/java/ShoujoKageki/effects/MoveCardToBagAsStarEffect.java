package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.powers.VoidPower;
import ShoujoKageki.util.ActionUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoveCardToBagAsStarEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MoveCardToBagEffect.class.getName());
    public AbstractCard card;
    private boolean justStart = true;

    public static float DURATION = Settings.ACTION_DUR_FAST;
    public static final float DISCARD_X = (float) Settings.WIDTH * 0.96F;
    public static final float DISCARD_Y = (float) Settings.HEIGHT * 0.06F;
    public static final float DRAW_PILE_X = (float) Settings.WIDTH * 0.04F;
    public static final float DRAW_PILE_Y = (float) Settings.HEIGHT * 0.06F;

    public MoveCardToBagAsStarEffect(AbstractCard card) {
        super();
        this.card = card;
        ActionUtils.resetBeforeMoving(card);
        duration = startingDuration = DURATION;
        AbstractDungeon.effectsQueue.add(new StarEffect(card, c -> {
            isDone = true;
            AbstractDungeon.effectsQueue.add(new RenderStarEffect(card));
        }));
    }

    @Override
    public void update() {
    }

    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {
    }
}
