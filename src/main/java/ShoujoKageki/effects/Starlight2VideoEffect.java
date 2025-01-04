package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.patches.LastWordPatch;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Starlight2VideoEffect extends AbstractGameEffect {

    private boolean startPlay = false;

    private VideoPlayer videoPlayer;

    private float videoWidth;
    private float videoHeight;
    private float renderX;
    private float renderY;
    private float renderW;
    private float renderH;

    private boolean inTail = false;

    private boolean playSound = false;


    public Starlight2VideoEffect() {
        this.duration = this.startingDuration = 2f;
        LastWordPatch.notShowPlayerPowerTip = true;

//        CardCrawlGame.sound.stop(AudioPatch.Sound_Last_Word);
//        CardCrawlGame.sound.play(AudioPatch.Sound_Last_Word);
        this.color = Color.WHITE.cpy();
        this.color.a = 0.7f;

        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        videoPlayer.setOnVideoSizeListener((w, h) -> {
//            videoWidth = w - 30;  // fix video error
            videoWidth = w;
            videoHeight = h;

            float scale = Settings.WIDTH /  videoWidth; //  * 0.7f
            renderW = videoWidth * scale;
            renderH = videoHeight * scale;
            renderX = (Settings.WIDTH - renderW) / 2f;
            renderY = (Settings.HEIGHT - renderH) / 2f;
        });

        CardCrawlGame.sound.play(AudioPatch.Karen_starlight02_lightoff);
        AbstractDungeon.overlayMenu.showBlackScreen();
        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath("karen_starlight2.webm")));
                videoPlayer.setVolume(0);
//                videoPlayer.pause();
                startPlay = true;
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

//        if (!startPlay && (this.startingDuration - this.duration >= 1f)) {
//            startPlay = true;
//            videoPlayer.resume();
////            AbstractDungeon.overlayMenu.showBlackScreen();
//        }

        if (startPlay) {
            videoPlayer.update();
        }

//        if (!playSound && this.startingDuration - this.duration >= 0.5f) {
//            playSound = true;
//            CardCrawlGame.sound.play(AudioPatch.Karen_starlight02_lightoff);
//        }

        if (!inTail && (this.startingDuration - this.duration >= 1.2f)) {
            inTail = true;
//            forceHideBlackScreen();
            AbstractDungeon.overlayMenu.hideBlackScreen();
        }
        if (inTail && (this.startingDuration - this.duration >= 1.5)) {
            over();
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
                sb.draw(texture, renderX, renderY, renderW, renderH, 0, 0, (int) videoWidth, (int) videoHeight, false, false);
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

    private void forceHideBlackScreen() {
        Color blackScreenColor = ReflectionHacks.getPrivate(AbstractDungeon.overlayMenu, OverlayMenu.class, "blackScreenColor");
        blackScreenColor.a = 0f;
        AbstractDungeon.overlayMenu.hideBlackScreen();
    }
}
