package thief.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;

public class DrawMoreByLastDrawAction extends AbstractGameAction {
    private final Predicate<AbstractCard> predicate;
    private final int drawMore;

    public DrawMoreByLastDrawAction(Predicate<AbstractCard> predicate, int drawMore) {
        this.predicate = predicate;
        this.drawMore = drawMore;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }


    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (!DrawCardAction.drawnCards.isEmpty()) {
                AbstractCard lastDrawn = DrawCardAction.drawnCards.get(DrawCardAction.drawnCards.size() - 1);
                if (predicate.test(lastDrawn)) {
                    addToBot(new DrawCardAction(drawMore));
                }
            }
        }
        isDone = true;
    }
}
