package ShoujoKageki_Karen.screen;

import ShoujoKageki.Log;
import ShoujoKageki.reskin.skin.AbstractSkin;
import ShoujoKageki.reskin.skin.SkinManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public class SkinSelectScreen {
    public Hitbox leftHb;
    public Hitbox rightHb;
    public Hitbox linkHb;
    public TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationStateData stateData;
    public AnimationState state;
    public String curName = "";
    public String info = "";
    public String link = "";

    public float offsetX = Settings.WIDTH * 0.05f;
    public float offsetY = -Settings.HEIGHT * 0.06f;
    private static final GlyphLayout gl = new GlyphLayout();

    public SkinSelectScreen() {
        this.refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
//        this.linkHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
    }

    public void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        Log.logger.info("loadAnimation {}", atlasUrl);
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
//        Log.logger.info("===== loadAnimation {} ========", atlasUrl);
//        AnimationState.TrackEntry e = this.state.setAnimation(0, "Skill2_Idle", true);
//        e.setTimeScale(1.5F);
    }

    public void refresh() {
        if (CardCrawlGame.chosenCharacter == null) return;
        AbstractSkin skin = SkinManager.getCurSkin(CardCrawlGame.chosenCharacter);
        if (skin == null) return;
        this.curName = skin.name;
        this.info = skin.info;
        this.link = skin.jumpUrl;
        if (!StringUtils.isEmpty(this.link) && !StringUtils.isEmpty(this.info)) {
            gl.setText(FontHelper.cardDescFont_N, this.info, Color.WHITE, 0.0F, 1, false);
            this.linkHb = new Hitbox(gl.width, gl.height);
        } else {
            this.linkHb = null;
        }
        this.loadAnimation(skin.atlasUrl, skin.skeletonUrl, 1.5F);
        SkinManager.loadSkin(AbstractDungeon.player);
    }

    public void changeIndex(int change) {
        List<AbstractSkin> abstractSkins = SkinManager.skinMap.get(CardCrawlGame.chosenCharacter);
        int curSkinIndex = SkinManager.getCurSkinIndex(CardCrawlGame.chosenCharacter);
        curSkinIndex = (curSkinIndex + change + abstractSkins.size()) % abstractSkins.size();
        SkinManager.skinSelected.put(CardCrawlGame.chosenCharacter.name(), curSkinIndex);
    }

    public void update() {
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float) Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 150.0F * Settings.scale + offsetX, centerY + offsetY);
        this.rightHb.move(centerX + 150.0F * Settings.scale + offsetX, centerY + offsetY);
        if (this.linkHb != null) {
            this.linkHb.move(centerX + offsetX, centerY + offsetY - Settings.HEIGHT * 0.06f);
        }
        this.updateInput();
    }

    private void updateInput() {
        if (SkinManager.isCharHasSkin(CardCrawlGame.chosenCharacter)) {
            this.leftHb.update();
            this.rightHb.update();
            if (this.linkHb != null) this.linkHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.changeIndex(1);
                this.refresh();
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.changeIndex(-1);
                this.refresh();
            }

            if (this.linkHb != null && this.linkHb.clicked) {
                this.linkHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                Gdx.net.openURI(link);
            }


            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }

                if (this.linkHb != null && this.linkHb.hovered) {
                    this.linkHb.clickStarted = true;
                }
            }
        }

    }

    public void render(SpriteBatch sb) {
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float) Settings.HEIGHT * 0.5F;
        this.renderSkin(sb, centerX + offsetX, centerY + offsetY);
//        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "TMP TEXT", centerX + offsetX, centerY + 250.0F * Settings.scale + offsetY, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX + offsetX, centerY + offsetY - Settings.HEIGHT * 0.02f, Color.WHITE);

        Color linkColor = (this.linkHb != null && this.linkHb.hovered) ? Color.LIGHT_GRAY.cpy() : Color.WHITE.cpy();
        linkColor.a /= 2f;
        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, this.info, centerX + offsetX, centerY + offsetY - Settings.HEIGHT * 0.06f, linkColor);

        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if (this.atlas != null) {
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(x, y);
            sb.end();
            CardCrawlGame.psb.begin();
            AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        }
    }
}
