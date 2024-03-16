package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.patches.LastWordPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LastWordVideoEffect extends AbstractGameEffect {

    private boolean startPlay = false;

    private VideoPlayer videoPlayer;

    private float videoWidth;
    private float videoHeight;
    private float renderX;
    private float renderY;
    private float renderW;
    private float renderH;

    private float expandRate = 0.1f;

    private boolean inTail = false;


    public LastWordVideoEffect() {
        this.duration = this.startingDuration = 2f;
        LastWordPatch.notShowPlayerPowerTip = true;

        CardCrawlGame.music.silenceBGM();
        CardCrawlGame.sound.stop(AudioPatch.Sound_Last_Word);
        CardCrawlGame.sound.play(AudioPatch.Sound_Last_Word);
        this.color = Color.WHITE.cpy();

        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        videoPlayer.setOnVideoSizeListener((w, h) -> {
            videoWidth = w - 30;  // fix video error
            videoHeight = h;
            float scale = videoHeight / Settings.HEIGHT * 0.7f;
            renderW = videoWidth * scale;
            renderH = videoHeight * scale;
            renderX = (Settings.WIDTH - renderW) / 2f;
            renderY = (Settings.HEIGHT - renderH) / 2f;
        });

        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath("last_word.webm")));
                videoPlayer.setVolume(0);
                videoPlayer.pause();
            } catch (Exception e) {
                Log.logger.error("", e);
                e.printStackTrace();
                over();
            }
        }).start();
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (!startPlay && (this.startingDuration - this.duration >= 1f)) {
            startPlay = true;
            videoPlayer.resume();
            AbstractDungeon.overlayMenu.showBlackScreen();
        }

        if (startPlay) {
            videoPlayer.update();
            expandRate = MathUtils.lerp(expandRate, 1f, Gdx.graphics.getDeltaTime() * 6.0F);
            if (expandRate > 1f) expandRate = 1f;
        }

        if (!inTail && (this.startingDuration - this.duration >= 6.5f)) {
            inTail = true;
            AbstractDungeon.overlayMenu.hideBlackScreen();
        }
        if (inTail) {
            this.color.a = MathUtils.lerp(this.color.a, 0, Gdx.graphics.getDeltaTime() * 5f);
            if (this.color.a <= 0.01f) {
                over();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (startPlay) {
            Texture texture = videoPlayer.getTexture();
            if (texture != null) {
                sb.setColor(this.color);

                float offsetW = (1f - expandRate) * renderW / 2f;
                sb.draw(texture, renderX + offsetW, renderY, renderW - offsetW * 2, renderH, (int) ((1 - expandRate) * videoWidth / 2), 0, (int) (expandRate * videoWidth), (int) videoHeight, false, false);
            }
        }
    }

    @Override
    public void dispose() {

    }

    private void over() {
        if (videoPlayer != null) {
            videoPlayer.dispose();
            videoPlayer = null;
        }
        isDone = true;
        LastWordPatch.notShowPlayerPowerTip = false;
        AbstractDungeon.overlayMenu.hideBlackScreen();
    }
}
