package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.effects.patch.StarEffectPatch;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class StarEffect extends AbstractGameEffect {

    private final AbstractCard card;
    private final Soul soul;
    private Consumer<AbstractCard> callback;

    public StarEffect(AbstractCard card, float target_x, float target_y, Consumer<AbstractCard> callback) {
        this.card = card;
        this.callback = callback;
        soul = new Soul();
        StarEffectPatch.Field.asStar.set(soul, true);
        soul.discard(card, true);
        soul.group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        setTarget(soul, new Vector2(target_x, target_y));
        setRotation(soul, 0f);
        card.targetDrawScale = 0.01f;
        card.drawScale = 0.2f;
        this.startingDuration = this.duration = Settings.ACTION_DUR_FAST;
        this.color = Color.WHITE.cpy();
    }

    public StarEffect(AbstractCard card, Consumer<AbstractCard> callback) {
        this(card, MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F),
                MathUtils.random((float) Settings.HEIGHT * 0.7F, (float) Settings.HEIGHT * 0.9F),
                callback);
    }

    @Override
    public void update() {
        if (!soul.isReadyForReuse) {
            soul.update();
        } else {
            isDone = true;
            if (callback != null) {
                callback.accept(card);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!soul.isReadyForReuse) {
            soul.render(sb);
        }
    }

    @Override
    public void dispose() {

    }

    private static void setTarget(Soul soul, Vector2 target) {
        Field field = ReflectionHacks.getCachedField(Soul.class, "target");
        try {
            field.set(soul, target);
        } catch (IllegalAccessException e) {
            Log.logger.error("", e);
        }
    }

    private static void setRotation(Soul soul, float rotation) {
        Field field = ReflectionHacks.getCachedField(Soul.class, "rotation");
        try {
            field.set(soul, rotation);
        } catch (IllegalAccessException e) {
            Log.logger.error("", e);
        }
    }
}
