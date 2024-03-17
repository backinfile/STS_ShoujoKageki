package ShoujoKageki.screen;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.reskin.patches.SelectScreenPatch;
import ShoujoKageki.screen.patch.CharSelectPlayVideoPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;

import java.util.Set;

public class CharSelectPlayVideoScreen {
    public static long lastShowVideoTime = 0;

    private static final long MIN = 1000 * 60;

    private VideoPlayer videoPlayer;
    private float renderX;
    private float renderY;
    private float renderW;
    private float renderH;

    private float waitToCloseTime;
    private Texture lastTexture;

    static class Lazy {
        public static final Texture BLACK_TEXTURE;

        static {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.BLACK);
            pixmap.fill();
            BLACK_TEXTURE = new Texture(pixmap);
        }
    }

    public void start() {
        if (CharSelectPlayVideoPatch.inPlayVideo || System.currentTimeMillis() - lastShowVideoTime < MIN) {
            CardCrawlGame.sound.stop(AudioPatch.Sound_Karen_OnSelect);
            CardCrawlGame.sound.play(AudioPatch.Sound_Karen_OnSelect); // Sound Effect
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); // Screen
            return;
        }

        CharSelectPlayVideoPatch.inPlayVideo = true;
        CardCrawlGame.music.silenceBGMInstantly();
        waitToCloseTime = -1;
        lastTexture = null;


        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        videoPlayer.setOnVideoSizeListener((w, h) -> {
            float rateW = w / Settings.WIDTH;
            float rateH = h / Settings.HEIGHT;

            if (false) { // rateW > rateH
                float rate = Settings.WIDTH / w;
                renderW = w * rate;
                renderH = h * rate;
            } else {
                float rate = Settings.HEIGHT / h;
                renderW = w * rate;
                renderH = h * rate;
//                Log.logger.info("========  w = {} rate = {} renderW = {} Width={}", w, rate, renderW, Settings.WIDTH);
            }
            renderX = (Settings.WIDTH - renderW) / 2f;
            renderY = (Settings.HEIGHT - renderH) / 2f;
        });
        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath("karen_on_select.webm")));
//                videoPlayer.setVolume(0f);
            } catch (Exception e) {
                Log.logger.error("", e);
                e.printStackTrace();
                over();
            }
        }).start();

//        CardCrawlGame.sound.stop(AudioPatch.Sound_Karen_OnSelect);
//        CardCrawlGame.sound.play(AudioPatch.Sound_Karen_OnSelect); // Sound Effect
    }

    public void update() {
        videoPlayer.update();

        if (waitToCloseTime > 0) {
            waitToCloseTime -= Gdx.graphics.getDeltaTime();
            if (waitToCloseTime <= 0) {
                overInstantly();
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(Lazy.BLACK_TEXTURE, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        Texture texture = videoPlayer.getTexture();
        if (waitToCloseTime > 0 && lastTexture != null) {
            Color color = Color.WHITE.cpy();
            color.a = waitToCloseTime;
            sb.setColor(color);
            sb.draw(lastTexture, renderX, renderY, renderW, renderH);
        } else if (texture != null) {
            lastTexture = texture;
            sb.setColor(Color.WHITE);
            sb.draw(texture, renderX, renderY, renderW, renderH);
        }
    }

    public void over() {
//        waitToCloseTime = 0.5f;
        lastShowVideoTime = System.currentTimeMillis();
        overInstantly();
    }

    public void overInstantly() {
        waitToCloseTime = -1f;
        CharSelectPlayVideoPatch.inPlayVideo = false;
        CardCrawlGame.music.unsilenceBGM();
        if (videoPlayer != null) {
            videoPlayer.dispose();
            videoPlayer = null;
        }
    }
}
