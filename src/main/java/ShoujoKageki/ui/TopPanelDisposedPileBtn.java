package ShoujoKageki.ui;

import ShoujoKageki.ModInfo;
import ShoujoKageki.screen.patch.DisposedPilePatch;
import ShoujoKageki.variables.patch.DisposableField;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class TopPanelDisposedPileBtn extends TopPanelItem {
    private static final Texture IMG = new Texture(ModInfo.makeUIPath("disposedPile.png"));
    public static final String ID = ModInfo.makeID(TopPanelDisposedPileBtn.class.getSimpleName());
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = UI_STRINGS.TEXT;
    private boolean isOpen = false;
    private final static Color DISABLE_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);

    public TopPanelDisposedPileBtn() {
        super(IMG, ID);
    }

    @Override
    protected void onClick() {
        if (this.isOpen && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
            this.isOpen = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
        } else if (!AbstractDungeon.isScreenUp) {
            if (DisposableField.getDisposedPile().isEmpty()) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, TEXT[2], true));
            } else {
                DisposedPilePatch.showScreen = true;
                AbstractDungeon.exhaustPileViewScreen.open();
                this.isOpen = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        boolean disable = isDisable();
        render(sb, disable ? DISABLE_COLOR : Color.WHITE);

        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelAmountFont,
                Integer.toString(DisposableField.getDisposedPile().size()),
                this.x + 58.0F * Settings.scale,
                this.y + 25.0F * Settings.scale, Color.WHITE.cpy());

        if (!disable && this.getHitbox().hovered) {
            sb.setColor(Color.CYAN);
            if (!isOpen) {
                TipHelper.renderGenericTip(TOP_RIGHT_TIP_X, TIP_Y, TEXT[0], TEXT[1]);
            }
        }
    }

    private boolean isDisable() {
        return AbstractDungeon.isScreenUp;
    }

    private static final float TIP_Y = (float) Settings.HEIGHT - 120.0F * Settings.scale;
    private static final float TOP_RIGHT_TIP_X = 1550.0F * Settings.scale;
}
