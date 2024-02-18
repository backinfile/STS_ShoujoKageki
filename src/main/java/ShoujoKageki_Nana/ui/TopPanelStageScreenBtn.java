package ShoujoKageki_Nana.ui;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.screen.patch.DisposedPilePatch;
import ShoujoKageki_Karen.variables.patch.DisposableField;
import ShoujoKageki_Nana.NanaPath;
import ShoujoKageki_Nana.screen.StageScreen;
import basemod.BaseMod;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.HashSet;
import java.util.Set;

public class TopPanelStageScreenBtn extends TopPanelItem {
    private static final Texture IMG = new Texture(NanaPath.makeUIPath("stage_icon.png"));
    public static final String ID = NanaPath.makeID(TopPanelStageScreenBtn.class.getSimpleName());
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = UI_STRINGS.TEXT;
    private boolean isOpen = false;
    private final static Color DISABLE_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);

    private float targetAngle = 0f;
    private static final Set<AbstractDungeon.CurrentScreen> validScreens;

    static {
        validScreens = new HashSet<>();
        validScreens.add(AbstractDungeon.CurrentScreen.COMBAT_REWARD);
        validScreens.add(AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW);
        validScreens.add(AbstractDungeon.CurrentScreen.DEATH);
        validScreens.add(AbstractDungeon.CurrentScreen.BOSS_REWARD);
        validScreens.add(AbstractDungeon.CurrentScreen.SHOP);
        validScreens.add(AbstractDungeon.CurrentScreen.MAP);
    }


    public TopPanelStageScreenBtn() {
        super(IMG, ID);
        this.setX(Settings.WIDTH - (64f + 10f) * 5 * Settings.scale);
        this.setY(Settings.HEIGHT - 64.0F * Settings.scale);
    }

    @Override
    protected void onClick() {
        if (this.isOpen) {
            this.isOpen = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
            return;
        }

        if (!AbstractDungeon.isScreenUp) {
            open();
            return;
        }

        switch (AbstractDungeon.screen) {
            case COMBAT_REWARD:
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
                open();
                break;
            case MASTER_DECK_VIEW:
                AbstractDungeon.closeCurrentScreen();
                open();
                break;
            case DEATH:
                AbstractDungeon.deathScreen.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
                open();
                break;
            case BOSS_REWARD:
                AbstractDungeon.bossRelicScreen.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.BOSS_REWARD;
                open();
                break;
            case SHOP:
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.SHOP;
                open();
                break;
            case MAP:
                if (AbstractDungeon.dungeonMapScreen.dismissable) {
                    if (AbstractDungeon.previousScreen != null) {
                        AbstractDungeon.screenSwap = true;
                    }

                    AbstractDungeon.closeCurrentScreen();
                } else {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.MAP;
                }

                open();
                break;
        }
    }

    private void open() {
//        AbstractPlayer p = AbstractDungeon.player;
//        AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, TEXT[2], true));
//        AbstractDungeon.previousScreen = AbstractDungeon.screen;

//        DisposedPilePatch.showScreen = true;
//        AbstractDungeon.exhaustPileViewScreen.open();

        BaseMod.openCustomScreen(StageScreen.Enum.STAGE_SCREEN);
        this.isOpen = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        boolean clickable = isClickable();
        Color renderColor = clickable ? Color.WHITE : DISABLE_COLOR;

        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelAmountFont,
                Integer.toString(DisposableField.getDisposedPile().size()),
                this.x + 58.0F * Settings.scale,
                this.y + 25.0F * Settings.scale, renderColor);

        if (clickable && this.getHitbox().hovered) {
            sb.setColor(Color.CYAN);
            if (!isOpen) {
                TipHelper.renderGenericTip(TOP_RIGHT_TIP_X, TIP_Y, TEXT[0], TEXT[1]);
            }
        }
    }

    private boolean isOkToClick() {
//        Log.logger.info("previousScreen = " + AbstractDungeon.previousScreen);
        if (isOpen) {
            return true;
        }
        if (!AbstractDungeon.isScreenUp) {
            return true;
        }
        if (validScreens.contains(AbstractDungeon.screen)) {
            if (AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.NONE || AbstractDungeon.screen == AbstractDungeon.previousScreen) {
                return true;
            }
            if (AbstractDungeon.previousScreen == null) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void update() {
        if (isOpen) {
            if (AbstractDungeon.screen != StageScreen.Enum.STAGE_SCREEN) isOpen = false;
        }

        setClickable(isOkToClick());

        this.angle = MathHelper.angleLerpSnap(this.angle, this.targetAngle);
        super.update();
    }

    @Override
    protected void onHover() {
//        super.onHover();
        if (isClickable()) {
            this.tint.a = 0.25F;
            this.targetAngle = 180;
        }
    }

    @Override
    protected void onUnhover() {
//        super.onUnhover();
        if (isClickable()) {
            this.tint.a = 0.0F;
            this.targetAngle = 0f;
        }
    }

    private static final float TIP_Y = (float) Settings.HEIGHT - 120.0F * Settings.scale;
    private static final float TOP_RIGHT_TIP_X = 1550.0F * Settings.scale;
}
