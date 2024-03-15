package ShoujoKageki.screen;

import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.screen.patch.GiraffeSplashScreenPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class GiraffeSplashScreen {
    public boolean isDone = false;
    private final Texture img = ImageMaster.loadImage(ModInfo.makeUIPath("GiraffeSplash.png"));
    private static final float SPEED = 4f;
    private static final float SCALE = 0.4f;

    private float rotation = 0;
    private float timeLeft = 10;
    private long musicId;

    public GiraffeSplashScreen() {
        musicId = CardCrawlGame.sound.play(AudioPatch.Sound_Revue);
    }

    public void close() {
        CardCrawlGame.sound.stop(AudioPatch.Sound_Revue);
        GiraffeSplashScreenPatch.setOver();
    }

    public void update() {
        if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) {
            close();
            return;
        }

        this.rotation += 360f / 60f * Gdx.graphics.getRawDeltaTime() * SPEED;
        if (rotation > 360) rotation -= 360;

        timeLeft -= Gdx.graphics.getDeltaTime();
        if (timeLeft <= 0) {
            close();
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        sb.setColor(Color.WHITE);

        float width = this.img.getWidth() * Settings.scale * SCALE;
        float height = this.img.getHeight() * Settings.scale * SCALE;
        sb.draw(this.img, (Settings.WIDTH - width) / 2f, (Settings.HEIGHT - height) / 2f,
                width / 2f, height / 2f, width, height,
                1f, 1f, rotation,
                0, 0, img.getWidth(), img.getHeight(),
                false, false);
    }
}
