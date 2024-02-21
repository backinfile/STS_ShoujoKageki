package ShoujoKageki_Nana.screen;

import ShoujoKageki.base.AbstractDefaultCard;
import ShoujoKageki_Nana.stage.StageFieldPatch;
import ShoujoKageki_Nana.stage.StagePosition;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Set;

public class StageViewRender {
    public static StageViewRender Inst = new StageViewRender();

    private final ArrayList<AbstractCard> cards = new ArrayList<>();
    private static int HandSize = 10;
    private int handPage = 0;


    public void initCards() {
        handPage = 0;
        cards.clear();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            cards.add(card.makeSameInstanceOf());
        }
        placeCards(true);
    }

    public void update() {
        for (AbstractCard card : cards) {
            card.hb.update();
            card.update();
        }
        for (AbstractCard card : cards) {
            StagePosition stagePosition = StageFieldPatch.getStagePosition(card);
            if (stagePosition.invalid) continue;
        }
    }

    public void render(SpriteBatch sb) {
        for (AbstractCard card : cards) {
            card.render(sb);
        }
    }

    private void placeCards(boolean init) {

        float stage_card_scale = 0.6f;

        float offsetX = 0f;
        float offsetY = -20 * Settings.scale;

        float centerX = Settings.WIDTH / 4f;
        float centerY = Settings.HEIGHT / 2f;
        float card_width = AbstractCard.IMG_WIDTH * Settings.scale * stage_card_scale;
        float card_height = AbstractCard.IMG_HEIGHT * Settings.scale * stage_card_scale;
        float gap = card_width / 4f;

        for (AbstractCard card : cards) {
            card.isFlipped = false;
            card.targetDrawScale = stage_card_scale;

            StagePosition stagePosition = StageFieldPatch.getStagePosition(card);
            if (stagePosition.invalid) {
                card.target_x = 0f;
                card.target_y = 0f;
            } else {
                card.target_x = (float) (stagePosition.getX() + 0f) * (card_width + gap) + centerX + offsetX;
                card.target_y = (float) (stagePosition.getY() + 0f) * (card_height + gap) + centerY + offsetY;
            }
            if (init) {
                card.drawScale = 0.3f;
                card.current_x = card.target_x;
                card.current_y = card.target_y;
            }
        }
    }
}
