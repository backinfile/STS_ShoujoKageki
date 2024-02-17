package ShoujoKageki.screen;

import ShoujoKageki.ModInfo;
import ShoujoKageki.karen.cards.patches.field.BagField;
import basemod.BaseMod;
import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;

import java.util.*;

public class BagPileViewScreen extends CustomScreen implements ScrollBarListener {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public boolean isHovered = false;
    private static final int CARDS_PER_LINE = 5;
    private boolean grabbedScreen = false;
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final float SCROLL_BAR_THRESHOLD;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private float grabStartY;
    private float currentDiffY;
    private static final String HEADER_INFO;
    private AbstractCard hoveredCard;
    private int prevDeckSize;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;


    public BagPileViewScreen() {
        this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        this.grabStartY = this.scrollLowerBound;
        this.currentDiffY = this.scrollLowerBound;
        this.hoveredCard = null;
        this.prevDeckSize = 0;
        this.controllerCard = null;
        drawStartX = (float) Settings.WIDTH;
        drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        this.scrollBar = new ScrollBar(this);
        this.scrollBar.changeHeight((float) Settings.HEIGHT - 384.0F * Settings.scale);
    }


    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen BAG_PILE_VIEW;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.BAG_PILE_VIEW;
    }

    public void update() {
        this.updateControllerInput();
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && !CInputHelper.isTopPanelActive()) {
            if ((float) Gdx.input.getY() > (float) Settings.HEIGHT * 0.7F) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            } else if ((float) Gdx.input.getY() < (float) Settings.HEIGHT * 0.3F) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
        }

        boolean isDraggingScrollBar = false;
        if (this.shouldShowScrollBar()) {
            isDraggingScrollBar = this.scrollBar.update();
        }

        if (!isDraggingScrollBar) {
            this.updateScrolling();
        }

        this.updatePositions();
        if (Settings.isControllerMode && this.controllerCard != null && !CInputHelper.isTopPanelActive()) {
            CInputHelper.setCursor(this.controllerCard.hb);
        }

    }

    private void updateControllerInput() {
        if (Settings.isControllerMode && !CInputHelper.isTopPanelActive()) {
            boolean anyHovered = false;
            int index = 0;

            for (Iterator var3 = BagField.getBag().group.iterator(); var3.hasNext(); ++index) {
                AbstractCard c = (AbstractCard) var3.next();
                if (c.hb.hovered) {
                    anyHovered = true;
                    break;
                }
            }

            if (!anyHovered) {
                Gdx.input.setCursorPosition((int) ((AbstractCard) BagField.getBag().group.get(0)).hb.cX, Settings.HEIGHT - (int) ((AbstractCard) BagField.getBag().group.get(0)).hb.cY);
                this.controllerCard = (AbstractCard) BagField.getBag().group.get(0);
            } else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && BagField.getBag().size() > 5) {
                index -= 5;
                if (index < 0) {
                    int wrap = BagField.getBag().size() / 5;
                    index += wrap * 5;
                    if (index + 5 < BagField.getBag().size()) {
                        index += 5;
                    }
                }

                Gdx.input.setCursorPosition((int) ((AbstractCard) BagField.getBag().group.get(index)).hb.cX, Settings.HEIGHT - (int) ((AbstractCard) BagField.getBag().group.get(index)).hb.cY);
                this.controllerCard = BagField.getBag().group.get(index);
            } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && BagField.getBag().size() > 5) {
                if (index < BagField.getBag().size() - 5) {
                    index += 5;
                } else {
                    index %= 5;
                }

                Gdx.input.setCursorPosition((int) BagField.getBag().group.get(index).hb.cX, Settings.HEIGHT - (int) ((AbstractCard) BagField.getBag().group.get(index)).hb.cY);
                this.controllerCard = (AbstractCard) BagField.getBag().group.get(index);
            } else if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                    if (index % 5 < 4) {
                        ++index;
                        if (index > BagField.getBag().size() - 1) {
                            index -= BagField.getBag().size() % 5;
                        }
                    } else {
                        index -= 4;
                        if (index < 0) {
                            index = 0;
                        }
                    }

                    Gdx.input.setCursorPosition((int) BagField.getBag().group.get(index).hb.cX, Settings.HEIGHT - (int) ((AbstractCard) BagField.getBag().group.get(index)).hb.cY);
                    this.controllerCard = BagField.getBag().group.get(index);
                }
            } else {
                if (index % 5 > 0) {
                    --index;
                } else {
                    index += 4;
                    if (index > BagField.getBag().size() - 1) {
                        index = BagField.getBag().size() - 1;
                    }
                }

                Gdx.input.setCursorPosition((int) ((AbstractCard) BagField.getBag().group.get(index)).hb.cX, Settings.HEIGHT - (int) ((AbstractCard) BagField.getBag().group.get(index)).hb.cY);
                this.controllerCard = (AbstractCard) BagField.getBag().group.get(index);
            }

        }
    }

    private void updatePositions() {
        this.hoveredCard = null;
        int lineNum = 0;
        ArrayList<AbstractCard> cards = BagField.getBag().group;

        for (int i = 0; i < cards.size(); ++i) {
            int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }

            cards.get(i).target_x = drawStartX + (float) mod * padX;
            cards.get(i).target_y = drawStartY + this.currentDiffY - (float) lineNum * padY;
            cards.get(i).update();
            if (AbstractDungeon.topPanel.potionUi.isHidden) {
                cards.get(i).updateHoverLogic();
                if (cards.get(i).hb.hovered) {
                    this.hoveredCard = cards.get(i);
                }
            }
        }

    }

    private void updateScrolling() {
        int y = InputHelper.mY;
        if (!this.grabbedScreen) {
            if (InputHelper.scrolledDown) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            } else if (InputHelper.scrolledUp) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }

            if (InputHelper.justClickedLeft) {
                this.grabbedScreen = true;
                this.grabStartY = (float) y - this.currentDiffY;
            }
        } else if (InputHelper.isMouseDown) {
            this.currentDiffY = (float) y - this.grabStartY;
        } else {
            this.grabbedScreen = false;
        }

        if (this.prevDeckSize != BagField.getBag().size()) {
            this.calculateScrollBounds();
        }

        this.resetScrolling();
        this.updateBarPosition();
    }

    private void calculateScrollBounds() {
        if (BagField.getBag().size() > BaseMod.MAX_HAND_SIZE) {
            int scrollTmp = BagField.getBag().size() / 5 - 2;
            if (BagField.getBag().size() % 5 != 0) {
                ++scrollTmp;
            }

            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float) scrollTmp * padY;
        } else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }

        this.prevDeckSize = BagField.getBag().size();
    }

    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        } else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }

    }

    public void reopen() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }

        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
    }

    @Override
    public void close() {
        AbstractDungeon.overlayMenu.hideBlackScreen();
        AbstractDungeon.isScreenUp = false;

        AbstractDungeon.overlayMenu.cancelButton.hide();
        genericScreenOverlayReset();
        for (AbstractCard c : BagField.getBag().group) {
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.12F;
//            c.teleportToDiscardPile();
//            c.darken(true);
            c.unhover();
        }
    }

    public void open() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }

        CardCrawlGame.sound.play("DECK_OPEN");
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.currentDiffY = this.scrollLowerBound;
        this.grabStartY = this.scrollLowerBound;
        this.grabbedScreen = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = curScreen();
//        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.DISCARD_VIEW;
        for (AbstractCard c : BagField.getBag().group) {
            c.setAngle(0.0F, true);
            c.targetDrawScale = 0.75F;
            c.drawScale = 0.75F;
            c.lighten(true);
        }

        this.hideCards();
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        if (BagField.getBag().group.size() <= 5) {
            drawStartY = (float) Settings.HEIGHT * 0.5F;
        } else {
            drawStartY = (float) Settings.HEIGHT * 0.66F;
        }
        this.calculateScrollBounds();
    }

    private void hideCards() {
        int lineNum = 0;
        ArrayList<AbstractCard> cards = BagField.getBag().group;

        for (int i = 0; i < cards.size(); ++i) {
            int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).current_x = drawStartX + (float) mod * padX;
            cards.get(i).current_y = drawStartY + this.currentDiffY - (float) lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
        }
    }

    public void render(SpriteBatch sb) {
        if (this.shouldShowScrollBar()) {
            this.scrollBar.render(sb);
        }

        if (this.hoveredCard == null) {
            BagField.getBag().render(sb);
        } else {
            BagField.getBag().renderExceptOneCard(sb, this.hoveredCard);
            this.hoveredCard.renderHoverShadow(sb);
            this.hoveredCard.render(sb);
            this.hoveredCard.renderCardTip(sb);
        }

//        sb.setColor(Color.WHITE);
//        sb.draw(ImageMaster.DISCARD_PILE_BANNER, 1290.0F * Settings.xScale, 0.0F, 630.0F * Settings.scale, 128.0F * Settings.scale);
//        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT[2], 1558.0F * Settings.xScale, 82.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);
        FontHelper.renderDeckViewTip(sb, HEADER_INFO, 96.0F * Settings.scale, Settings.CREAM_COLOR);
//        AbstractDungeon.overlayMenu.discardPilePanel.render(sb);
    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public boolean allowOpenDeck() {
        return false;
    }

    @Override
    public boolean allowOpenMap() {
        return false;
    }

    public void scrolledUsingBar(float newPercent) {
        this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        this.updateBarPosition();
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
        this.scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar() {
        return this.scrollUpperBound > SCROLL_BAR_THRESHOLD;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(BagPileViewScreen.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
        SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;
        HEADER_INFO = TEXT[0];
    }
}