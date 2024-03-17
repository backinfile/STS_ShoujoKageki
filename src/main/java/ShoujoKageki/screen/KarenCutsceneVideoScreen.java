package ShoujoKageki.screen;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;

public class KarenCutsceneVideoScreen {

    public boolean isOver = false;

    private VideoPlayer videoPlayer;
    private float renderX;
    private float renderY;
    private float renderW;
    private float renderH;


    private float hideBtnTimer = -1f;
    public ConfirmButton cancelButton = null;


    public void start() {
        CardCrawlGame.music.silenceBGMInstantly();
        CardCrawlGame.music.silenceTempBgmInstantly();

        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        videoPlayer.setOnVideoSizeListener((w, h) -> {
//            w -= 100; // fix video error
            float rate = Settings.HEIGHT / h * 0.7f;
            renderW = w * rate;
            renderH = h * rate;
            renderX = (Settings.WIDTH - renderW) / 2f;
            renderY = (Settings.HEIGHT - renderH) / 2f;
//            Log.logger.info("========  w = {} rate = {} renderW = {}", w, rate, renderW);
        });
        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath("end.webm")));
//                videoPlayer.setVolume(0f);
            } catch (Exception e) {
                Log.logger.error("", e);
                e.printStackTrace();
                over();
            }
        }).start();

        this.cancelButton = new ConfirmButton(KarenCutscene.UI_STRINGS.TEXT[0]);
        this.cancelButton.hb.clicked = false;
        this.cancelButton.isDisabled = true;
        this.cancelButton.hide();
    }

    public void update() {
        if (isOver) return;

        videoPlayer.update();
        cancelButton.update();

        if (InputHelper.isMouseDown || InputHelper.pressedEscape) {
            GameCursor.hidden = false;
            hideBtnTimer = 5f;
            this.cancelButton.show();
            this.cancelButton.isDisabled = false;
            Log.logger.info("show cancel");
        }


        if (this.cancelButton.hb.clicked) {
            this.cancelButton.hb.clicked = false;
            this.cancelButton.isDisabled = true;
            this.cancelButton.hide();
            over();
        }


        if (hideBtnTimer > 0) {
            hideBtnTimer -= Gdx.graphics.getDeltaTime();
            if (hideBtnTimer <= 0) {
                GameCursor.hidden = true;
                this.cancelButton.hb.clicked = false;
                this.cancelButton.isDisabled = true;
                this.cancelButton.hide();
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(CharSelectPlayVideoScreen.Lazy.BLACK_TEXTURE, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        if (videoPlayer != null) {
            Texture texture = videoPlayer.getTexture();
            if (texture != null) {
                sb.setColor(Color.WHITE);
                sb.draw(texture, renderX, renderY, renderW, renderH);
            }
        }
        this.cancelButton.render(sb);
    }

    public void over() {
        isOver = true;
        CardCrawlGame.music.unsilenceBGM();
        if (videoPlayer != null) {
            videoPlayer.dispose();
            videoPlayer = null;
        }

        GameCursor.hidden = false;
        AbstractDungeon.victoryScreen = new VictoryScreen(null);
    }
}
