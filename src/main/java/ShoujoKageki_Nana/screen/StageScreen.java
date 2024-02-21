package ShoujoKageki_Nana.screen;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StageScreen extends CustomScreen {
    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen STAGE_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.STAGE_SCREEN;
    }

    private void open() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;

        reopen();
    }

    @Override
    public void reopen() {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show("cancel_temp");
        StageViewRender.Inst.initCards();
    }

    @Override
    public void close() {

        AbstractDungeon.overlayMenu.cancelButton.hide();
        AbstractDungeon.overlayMenu.hideBlackScreen();
        AbstractDungeon.isScreenUp = false;
    }

    @Override
    public void update() {
        StageViewRender.Inst.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        StageViewRender.Inst.render(sb);

    }

    @Override
    public void openingSettings() {
        // Required if you want to reopen your screen when the settings screen closes
//        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public boolean allowOpenMap() {
        return true;
    }

    @Override
    public boolean allowOpenDeck() {
        return true;
    }
    // ...
}