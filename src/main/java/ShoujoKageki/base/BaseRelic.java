package ShoujoKageki.base;

import basemod.abstracts.CustomRelic;
import ShoujoKageki_Karen.KarenPath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class BaseRelic extends CustomRelic {
    public boolean screenLess = true;
    public int stokeWidth = 7;


    public BaseRelic(String id, String rawId, RelicTier tier) {
        super(id, "", tier, LandingSound.FLAT);
        makeTexture(rawId);
    }

    public BaseRelic(String id, String rawId, RelicTier tier, LandingSound landingSound) {
        super(id, "", tier, landingSound);
        makeTexture(rawId);
    }

    private void makeTexture(String rawId) {
//        Texture relicTexture = TextureLoader.getTexture(ModInfo.makeRelicPath(rawId + ".png"));
//        Texture outlineTexture = TextureLoader.getTexture(ModInfo.makeRelicOutlinePath(rawId + ".png"));
//        relicTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        outlineTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        this.setTextureOutline(relicTexture, outlineTexture);

        Pixmap texture = new Pixmap(Gdx.files.internal(KarenPath.makeRelicPath(rawId + ".png")));
        int width = texture.getWidth();
        int height = texture.getHeight();
//            Log.logger.info("=================== {} {} {}", width, height, rawId);
        Pixmap outline = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        outline.setColor(Color.WHITE);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (hasColorAround(texture, i, j)) {
                    outline.drawPixel(i, j);
                }
            }
        }
        outline.setColor(new Color(1f, 1f, 1f, 0.5f));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (outline.getPixel(i, j) != 0 && !allAdjColored(texture, i, j)) {
                    outline.drawPixel(i, j);
                }
            }
        }

        Texture relicTexture = new Texture(texture);
        Texture outlineTexture = new Texture(outline);
        relicTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        outlineTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.setTextureOutline(relicTexture, outlineTexture);
    }


    private boolean hasColorAround(Pixmap pixmap, int x, int y) {
        int radius = getStokeWidth() / 2;
        x -= radius;
        y -= radius;
        for (int i = 0; i < getStokeWidth(); i++) {
            for (int j = 0; j < getStokeWidth(); j++) {
                if ((i - radius) * (i - radius) + (j - radius) * (j - radius) <= radius * radius) {
                    if (pixmap.getPixel(i + x, j + y) != 0) return true;
                }
            }
        }
        return false;
    }

    private static final int[] DIR_X = {0, 0, -1, 1};
    private static final int[] DIR_Y = {1, -1, 0, 0};

    private static boolean allAdjColored(Pixmap pixmap, int x, int y) {
        for (int i = 0; i < 4; i++) {
            if (pixmap.getPixel(x + DIR_X[i], y + DIR_Y[i]) == 0) return false;
        }
        return true;
    }

    public void scaleToGetOutline(String rawId) {
        Gdx.app.postRunnable(() -> {
            Pixmap texture = new Pixmap(Gdx.files.internal(KarenPath.makeRelicPath(rawId + ".png")));
            int width = texture.getWidth();
            int height = texture.getHeight();
//            Log.logger.info("=================== {} {} {}", width, height, rawId);
            float scale = 0.85f;
            int offsetX = (int) Math.floor(width * (1f - scale) / 2f);
            int offsetY = (int) Math.floor(height * (1f - scale) / 2f);
            Pixmap outline = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            outline.setColor(Color.WHITE);
            outline.drawPixmap(texture,
                    offsetX, offsetY, width - offsetX * 2, height - offsetY * 2,
                    0, 0, width, height);

            Texture relicTexture = new Texture(texture);
            Texture outlineTexture = new Texture(outline);
            relicTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            outlineTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            this.setTextureOutline(relicTexture, outlineTexture);
        });
    }

    public int getStokeWidth() {
        return stokeWidth;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void triggerOnDead() {

    }

    public void triggerOnCardDisposed(AbstractCard card) {

    }


    public void triggerOnTakeFromBagToHand(AbstractCard card) {

    }

    public void triggerOnTakeFromBag(AbstractCard card) {

    }

    public void onSaveLoad() {

    }
}
