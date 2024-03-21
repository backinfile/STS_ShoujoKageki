package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.Res;
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

public class FormVideoEffect extends AbstractGameEffect {
    private VideoPlayer videoPlayer;

    private float videoWidth;
    private float videoHeight;
    private float renderX;
    private float renderY;
    private float renderW;
    private float renderH;

    private float moveDown = 1f;

    private boolean inTail = false;


    public FormVideoEffect() {
        this.duration = this.startingDuration = 2f;
        LastWordPatch.notShowPlayerPowerTip = true;

        CardCrawlGame.music.silenceBGM();
        CardCrawlGame.music.silenceTempBgmInstantly();
        this.color = Color.WHITE.cpy();

        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        videoPlayer.setOnVideoSizeListener((w, h) -> {
            videoWidth = w;
            videoHeight = h;
            float scale = videoHeight / Settings.HEIGHT * 0.7f / 0.8f;
            renderW = videoWidth * scale;
            renderH = videoHeight * scale;
            renderX = (Settings.WIDTH - renderW) / 2f;
            renderY = (Settings.HEIGHT - renderH) / 2f;
        });

        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath("form.webm")));
                videoPlayer.setVolume(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
            } catch (Exception e) {
                Log.logger.error("", e);
                e.printStackTrace();
                over();
            }
        }).start();
        AbstractDungeon.overlayMenu.showBlackScreen();
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        videoPlayer.update();
        moveDown = MathUtils.lerp(moveDown, 0f, Gdx.graphics.getDeltaTime() * 8.0F);
        if (moveDown < 0f) moveDown = 0f;

        if (!inTail && (this.startingDuration - this.duration >= 9f)) {
            inTail = true;
            AbstractDungeon.overlayMenu.hideBlackScreen();
        }
        if (inTail) {
            this.color.a = MathUtils.lerp(this.color.a, 0, Gdx.graphics.getDeltaTime() * 5f);
            if (this.color.a <= 0.01f) {
                this.color.a = 0;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Texture texture = videoPlayer.getTexture();
        if (texture != null) {
            sb.setColor(this.color);

            sb.draw(texture, renderX, renderY + renderH * moveDown, renderW, renderH * (1 - moveDown));
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
        AbstractDungeon.overlayMenu.hideBlackScreen();
        CardCrawlGame.music.playTempBgmInstantly(AudioPatch.Music_Form, true);
        AbstractDungeon.effectsQueue.add(new WhirlwindLongEffect(new Color(0.28f, 0.1f, 0.08f, 0.9f), true));
    }
}