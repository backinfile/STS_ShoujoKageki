package ShoujoKageki.ui;

import ShoujoKageki.relics.patch.TakeAllRewardCardsPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import ShoujoKageki.relics.BookMarchRelic;

public class TakeAllRewardCardsButton {
    public static final String TEXT;
    private static final int W = 512;
    private static final int H = 256;
    public static final float SHOW_X;
    private static final float HIDE_X;
    public static final float OFFSET_X;
    private float current_x;
    public float target_x;
    private Color textColor;
    private Color btnColor;
    private boolean isHidden;
    private RewardItem rItem;
    private float controllerImgTextWidth;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;

    public TakeAllRewardCardsButton() {
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.textColor = Color.WHITE.cpy();
        this.btnColor = Color.WHITE.cpy();
        this.isHidden = true;
        this.rItem = null;
        this.controllerImgTextWidth = 0.0F;
        this.hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        this.hb.move((float) Settings.WIDTH / 2.0F, SkipCardButton.TAKE_Y);
    }

    public void update() {
        if (!this.isHidden) {
            this.hb.update();
            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (this.hb.hovered && InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                CInputActionSet.proceed.unpress();
                this.hb.clicked = false;
                this.onClick();
                AbstractDungeon.closeCurrentScreen();
                this.hide();
            }

            if (this.current_x != this.target_x) {
                this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
                if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                    this.current_x = this.target_x;
                    this.hb.move(this.current_x, SkipCardButton.TAKE_Y);
                }
            }

            this.textColor.a = MathHelper.fadeLerpSnap(this.textColor.a, 1.0F);
            this.btnColor.a = this.textColor.a;
        }
    }

    public void onClick() {
        TakeAllRewardCardsPatch.record(this.rItem.cards);
        for (AbstractCard card : this.rItem.cards) {
            card.upgrade();
            AbstractDungeon.effectsQueue.add(new FastCardObtainEffect(card, card.current_x, card.current_y));
        }
        AbstractDungeon.player.getRelic(BookMarchRelic.ID).onTrigger();
        AbstractDungeon.combatRewardScreen.rewards.remove(this.rItem);
    }

    public void hide() {
        if (!this.isHidden) {
            this.isHidden = true;
        }

    }

    public void show(RewardItem rItem) {
        this.isHidden = false;
        this.textColor.a = 0.0F;
        this.btnColor.a = 0.0F;
        this.current_x = HIDE_X;
        this.target_x = SHOW_X;
        this.rItem = rItem;
    }

    public void render(SpriteBatch sb) {
        if (!this.isHidden) {
            this.renderButton(sb);
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT, this.current_x, SkipCardButton.TAKE_Y, this.textColor);
        }
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    private void renderButton(SpriteBatch sb) {
        sb.setColor(this.btnColor);
        sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - 256.0F, SkipCardButton.TAKE_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - 256.0F, SkipCardButton.TAKE_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            sb.setBlendFunction(770, 771);
        }

        if (Settings.isControllerMode) {
            if (this.controllerImgTextWidth == 0.0F) {
                this.controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT, 9999.0F, 0.0F);
            }

            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), this.current_x - 32.0F - this.controllerImgTextWidth / 2.0F - 38.0F * Settings.scale, SkipCardButton.TAKE_Y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        this.hb.render(sb);
    }

    static {
        TEXT = BookMarchRelic.STRINGS.DESCRIPTIONS[1];
        OFFSET_X = 165.0F;
        SHOW_X = (float) Settings.WIDTH / 2.0F;
        HIDE_X = (float) Settings.WIDTH / 2.0F;
        HITBOX_W = 260.0F * Settings.scale;
        HITBOX_H = 80.0F * Settings.scale;
    }
}