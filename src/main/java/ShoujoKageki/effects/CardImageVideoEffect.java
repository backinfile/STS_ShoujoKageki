package ShoujoKageki.effects;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CardImageVideoEffect extends AbstractGameEffect {
    public final AbstractCard card;
    private VideoPlayer videoPlayer;

    public CardImageVideoEffect(AbstractCard card, String videoFileName) {
        this.card = card;
        this.duration = this.startingDuration = 2f;

        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            over();
            return;
        }
        videoPlayer.setOnCompletionListener(e -> over());
        // do not block main thread
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(ModInfo.makeVideoPath(videoFileName)));
            } catch (Exception e) {
                Log.logger.error("", e);
                e.printStackTrace();
                over();
            }
        }).start();
    }

    @Override
    public void update() {
        if (videoPlayer != null) {
            videoPlayer.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Texture texture = videoPlayer.getTexture();
        if (texture != null) {
            sb.setColor(Color.WHITE);
            float drawX = card.current_x - 125.0F;
            float drawY = card.current_y - 95.0F;
            sb.draw(texture, drawX, drawY + 72.0F, 125.0F, 23.0F, 250.0F, 190.0F, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle, 0, 0, 250, 190, false, false);
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
    }

}