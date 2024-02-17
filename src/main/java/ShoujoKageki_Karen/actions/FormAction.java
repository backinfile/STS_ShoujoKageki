package ShoujoKageki_Karen.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FormAction extends AbstractGameAction {
    @Override
    public void update() {
        if (AbstractDungeon.actionManager.actions.stream().anyMatch(a -> a instanceof AquariumAction)) {
            // 需要在这个action后触发
            addToBot(new FormAction());
            isDone = true;
            return;
        }

        int toDraw = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        if (toDraw > 0) {
            addToTop(new DrawCardAction(toDraw));
        }
        isDone = true;
    }
}
