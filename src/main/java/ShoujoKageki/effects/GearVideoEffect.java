package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.patches.LastWordPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.Set;

public class GearVideoEffect extends AbstractGameEffect {
    private VideoPlayer videoPlayer;

    private int videoWidth;
    private int videoHeight;

    private boolean inTail = false;

    private static Texture mask = null;

    public GearVideoEffect() {
        this.duration = this.startingDuration = 2f;
        this.color = Color.WHITE.cpy();
        LastWordPatch.notShowPlayerPowerTip = true;

        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        videoPlayer.setOnVideoSizeListener((w, h) -> {
            videoWidth = (int) (w * Settings.scale * 1.3f);
            videoHeight = (int) (h * Settings.scale * 1.3f);
            genMaskTexture(videoWidth, videoHeight);
        });

        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath("Gear.webm")));
                videoPlayer.setVolume(0);
            } catch (Exception e) {
                Log.logger.error("", e);
                e.printStackTrace();
                over();
            }
        }).start();
    }

    private static void genMaskTexture(int videoWidth, int videoHeight) {
        Gdx.app.postRunnable(() -> {
            if (mask == null) {
                Pixmap pixmap = new Pixmap(videoWidth, videoHeight, Pixmap.Format.RGBA8888);
                int step = 30;
                for (int i = step; i >= 0; i--) {
                    pixmap.setColor(new Color(0, 0, 0, 1 - (1f / step) * i));
                    pixmap.fillCircle(videoWidth / 2, videoHeight / 2, (int) (videoWidth * 0.16f + videoWidth * 0.08f * i / step));
                }
                mask = new Texture(pixmap);
            }
        });
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.sound.play(AudioPatch.Sound_Gear);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        videoPlayer.update();

        if (!inTail && (this.startingDuration - this.duration >= 2.5f)) {
            inTail = true;
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
        if (mask != null) {
            Texture texture = videoPlayer.getTexture();
            if (texture != null) {
                float x = (Settings.WIDTH - videoWidth) / 2f;
                float y = (Settings.HEIGHT - videoHeight) / 2f;

//                sb.draw(texture, x, y, videoWidth, videoHeight);

                // apply mask
                Gdx.gl.glColorMask(false, false, false, true);
                sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
                sb.setColor(color);
                sb.draw(mask, x, y, videoWidth, videoHeight);
                sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA);
                sb.setColor(Color.WHITE);
                sb.draw(texture, x, y, videoWidth, videoHeight);

                // draw texture
                Gdx.gl.glColorMask(true, true, true, true);
                sb.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);
                sb.draw(texture, x, y, videoWidth, videoHeight);

                // over
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
    }
}
