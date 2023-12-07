package thief.screen;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import thief.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import static thief.ModInfo.makeID;
import static thief.screen.BlackMarketScreen.Enum.BLACK_MARKET;

public class BlackMarketScreen extends CustomScreen {
    private static Texture rugImg;
    private static final float DRAW_START_X = (float) Settings.WIDTH * 0.16F;
    private static final float TOP_ROW_Y = 760.0F * Settings.yScale;
    private static final float GOLD_IMG_OFFSET_X = -50.0F * Settings.scale;
    private static final float GOLD_IMG_OFFSET_Y = -215.0F * Settings.scale;
    private static final float PRICE_TEXT_OFFSET_X = 16.0F * Settings.scale;
    private static final float PRICE_TEXT_OFFSET_Y = -180.0F * Settings.scale;
    private static final float GOLD_IMG_WIDTH = (float) ImageMaster.UI_GOLD.getWidth() * Settings.scale;


    private static final String ID = makeID(BlackMarketScreen.class.getSimpleName());
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(ID);


    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private float purgeCardY;
    private boolean somethingHovered;
    private float rugY;
    private float notHoveredTimer;

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen BLACK_MARKET;
    }

    public BlackMarketScreen() {
        this.rugY = (float) Settings.HEIGHT / 2.0F + 540.0F * Settings.yScale;
    }

    public void init() {
        if (rugImg == null) {
            rugImg = ImageMaster.loadImage("images/npcs/rug/zhs.png");
        }

        initCards();
    }

    private void initCards() {
        int tmp = (int) ((float) Settings.WIDTH - DRAW_START_X * 2.0F - AbstractCard.IMG_WIDTH_S * 5.0F) / 4;
        float padX = (float) ((int) ((float) tmp + AbstractCard.IMG_WIDTH_S));

        cards.add(AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true));

        for (int i = 0; i < cards.size(); ++i) {
            AbstractCard card = cards.get(i);
            card.price = (int) (AbstractCard.getPrice(card.rarity) * AbstractDungeon.merchantRng.random(0.9F, 1.1F));
            card.current_x = (float) (Settings.WIDTH / 2);
            card.target_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float) i;
            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                relic.onPreviewObtainCard(card);
            }
        }
    }

    private void open() {
        Log.logger.info("==========open");
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        AbstractDungeon.screen = curScreen();
        reopen();
    }


    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return BLACK_MARKET;
    }

    @Override
    public void reopen() {
        Log.logger.info("==========reopen");

        CardCrawlGame.sound.play("SHOP_OPEN");
        this.setStartingCardPositions();
        this.purgeCardY = -1000.0F;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = curScreen();
//        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.show(uiString.TEXT[0]);

        this.rugY = (float)Settings.HEIGHT;
        AbstractDungeon.overlayMenu.showBlackScreen();
    }


    @Override
    public void update() {
        this.somethingHovered = false;
//        this.updateControllerInput();
//        this.updatePurgeCard();
//        this.updatePurge();
//        this.updateRelics();
//        this.updatePotions();
        this.updateRug();
//        this.updateSpeech();
        this.updateCards();
//        this.updateHand();
        AbstractCard hoveredCard = null;

        for (AbstractCard c : this.cards) {
            if (c.hb.hovered) {
                hoveredCard = c;
                this.somethingHovered = true;
//                this.moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0F, c.current_y);
                break;
            }
        }

        if (hoveredCard != null && InputHelper.justClickedLeft) {
            hoveredCard.hb.clickStarted = true;
        }

        if (hoveredCard != null && (InputHelper.justClickedRight || CInputActionSet.proceed.isJustPressed())) {
            InputHelper.justClickedRight = false;
            CardCrawlGame.cardPopup.open(hoveredCard);
        }

        if (hoveredCard != null && (hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed())) {
            hoveredCard.hb.clicked = false;
            this.purchaseCard(hoveredCard);
        }
    }

    @Override
    public void close() {
        AbstractDungeon.overlayMenu.hideBlackScreen();
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            RestRoom r = (RestRoom) AbstractDungeon.getCurrRoom();
            r.campfireUI.reopen();
        }
        Log.logger.info("==========close");
    }

    @Override
    public boolean allowOpenMap() {
        return true;
    }

    @Override
    public boolean allowOpenDeck() {
        return true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(rugImg, 0.0F, this.rugY, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        this.renderCardsAndPrices(sb);
//        sb.draw(handImg, this.handX + this.f_effect.x, this.handY + this.f_effect.y, HAND_W, HAND_H);
//        if (this.speechBubble != null) {
//            this.speechBubble.render(sb);
//        }

//        if (this.dialogTextEffect != null) {
//            this.dialogTextEffect.render(sb);
//        }
    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public void openingDeck() {
        super.openingDeck();
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public void openingMap() {
        super.openingMap();
        AbstractDungeon.previousScreen = curScreen();
    }

    private void purchaseCard(AbstractCard hoveredCard) {
        if (AbstractDungeon.player.gold >= hoveredCard.price) {
            CardCrawlGame.metricData.addShopPurchaseData(hoveredCard.getMetricID());
            AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(hoveredCard, hoveredCard.current_x, hoveredCard.current_y));
            AbstractDungeon.player.loseGold(hoveredCard.price);
            CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
            this.cards.remove(hoveredCard);
            InputHelper.justClickedLeft = false;
            this.notHoveredTimer = 1.0F;
//            this.speechTimer = MathUtils.random(40.0F, 60.0F);
            this.playBuySfx();
//            this.createSpeech(getBuyMsg());
        } else {
//            this.speechTimer = MathUtils.random(40.0F, 60.0F);
            this.playCantBuySfx();
//            this.createSpeech(getCantBuyMsg());
        }

    }


    private void renderCardsAndPrices(SpriteBatch sb) {
        for (AbstractCard c : this.cards) {
            c.render(sb);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, c.current_x + GOLD_IMG_OFFSET_X, c.current_y + GOLD_IMG_OFFSET_Y - (c.drawScale - 0.75F) * 200.0F * Settings.scale, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            Color color = Color.WHITE.cpy();
            if (c.price > AbstractDungeon.player.gold) {
                color = Color.SALMON.cpy();
            }
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(c.price), c.current_x + PRICE_TEXT_OFFSET_X, c.current_y + PRICE_TEXT_OFFSET_Y - (c.drawScale - 0.75F) * 200.0F * Settings.scale, color);
        }

        for (AbstractCard card : this.cards) {
            card.renderCardTip(sb);
        }
    }

    private void setPrice(AbstractCard card) {
        float tmpPrice = (float) AbstractCard.getPrice(card.rarity) * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
        if (card.color == AbstractCard.CardColor.COLORLESS) {
            tmpPrice *= 1.2F;
        }

        if (AbstractDungeon.player.hasRelic("The Courier")) {
            tmpPrice *= 0.8F;
        }

        if (AbstractDungeon.player.hasRelic("Membership Card")) {
            tmpPrice *= 0.5F;
        }

        card.price = (int) tmpPrice;
    }

    private void setStartingCardPositions() {
        int tmp = (int) ((float) Settings.WIDTH - DRAW_START_X * 2.0F - AbstractCard.IMG_WIDTH_S * 5.0F) / 4;
        float padX = (float) ((int) ((float) tmp + AbstractCard.IMG_WIDTH_S)) + 10.0F * Settings.scale;

        int i;
        for (i = 0; i < this.cards.size(); ++i) {
            AbstractCard card = this.cards.get(i);
            card.updateHoverLogic();
            card.targetDrawScale = 0.75F;
            card.current_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float) i;
            card.target_x = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * (float) i;
            card.target_y = 9999.0F * Settings.scale;
            card.current_y = 9999.0F * Settings.scale;
        }
    }

    private void updateRug() {
        if (this.rugY != 0.0F) {
            float target = (float) Settings.HEIGHT / 2.0F - 540.0F * Settings.yScale;
            this.rugY = MathUtils.lerp(this.rugY, target, Gdx.graphics.getDeltaTime() * 5.0F);
            if (Math.abs(this.rugY - 0.0F) < 0.5F) {
                this.rugY = 0.0F;
            }
        }

    }


    private void updateCards() {
        for (AbstractCard card : this.cards) {
            card.update();
            card.updateHoverLogic();
            card.current_y = this.rugY + TOP_ROW_Y;
            card.target_y = card.current_y;
        }
    }

    public void playBuySfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_KA");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_KB");
        } else {
            CardCrawlGame.sound.play("VO_MERCHANT_KC");
        }
    }

    public void playCantBuySfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_2B");
        } else {
            CardCrawlGame.sound.play("VO_MERCHANT_2C");
        }
    }
}
