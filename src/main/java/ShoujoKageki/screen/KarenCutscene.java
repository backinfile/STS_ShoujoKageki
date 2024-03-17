package ShoujoKageki.screen;

import ShoujoKageki.ModInfo;
import ShoujoKageki.screen.KarenCutsceneVideoScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KarenCutscene extends Cutscene {
    private final KarenCutsceneVideoScreen screen = new KarenCutsceneVideoScreen();
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(KarenCutscene.class.getSimpleName()));

    public KarenCutscene(AbstractPlayer.PlayerClass chosenClass) {
        super(chosenClass);
        screen.start();
    }

    @Override
    public void update() {
        screen.update();
        if (screen.isOver) {
            dispose();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!screen.isOver) {
            screen.render(sb);
        }
    }

    @Override
    public void renderAbove(SpriteBatch sb) {
    }
}
