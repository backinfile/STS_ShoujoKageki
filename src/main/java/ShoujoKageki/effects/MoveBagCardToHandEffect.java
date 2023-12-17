package ShoujoKageki.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoveBagCardToHandEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MoveCardToBagEffect.class.getName());
    public AbstractCard card;
    private boolean justStart = true;

    public static float DURATION = Settings.ACTION_DUR_FAST;

    public MoveBagCardToHandEffect(AbstractCard card) {
        super();
        this.card = card;
        duration = startingDuration = DURATION;
//        ActionUtils.resetBeforeMoving(card);
        AbstractDungeon.player.hand.addToHand(card);
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.onCardDrawOrDiscard();
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
