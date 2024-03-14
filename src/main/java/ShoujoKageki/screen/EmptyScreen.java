package ShoujoKageki.screen;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EmptyScreen extends CustomScreen {
    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen EmptyScreen;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.EmptyScreen;
    }

    public void open() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        AbstractDungeon.screen = curScreen();
        reopen();
    }

    @Override
    public void reopen() {
        AbstractDungeon.isScreenUp = true;
//        AbstractDungeon.overlayMenu.showBlackScreen();
    }

    @Override
    public void close() {
        AbstractDungeon.isScreenUp = false;
//        AbstractDungeon.overlayMenu.hideBlackScreen();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void openingSettings() {
    }

    @Override
    public boolean allowOpenMap() {
        return false;
    }

    @Override
    public boolean allowOpenDeck() {
        return false;
    }
}
