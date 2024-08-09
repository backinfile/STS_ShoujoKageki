package ShoujoKagekiCore.base;

import ShoujoKagekiCore.CoreModPath;
import ShoujoKagekiCore.Log;
import ShoujoKagekiCore.shine.DisposableVariable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class BaseCard extends AbstractDefaultCard {

    public static final int DEFAULT_SHINE_CNT = 9;
    public static final int MEDIUM_SHINE_CNT = 6;
    public static final int LOW_SHINE_CNT = 3;

    public int bagCardPreviewNumber = 0; // 仅用于显示，表示即将从约定牌堆抽出几张
    public int bagCardPreviewSelectNumber = 0; // 仅用于显示，表示即将从约定牌堆选择几张
    public boolean bagCardPreviewExchange = false; // 仅用于显示，表示即将从约定牌堆交换
    public int stageLightForTarget = 0; // 舞台灯光特效

    private long previewCardsListLastChangeTime = -1;
    private int previewCardsListIndex = 0;
    private List<AbstractCard> cardsToPreviewList = null;
    public CardStrings cardStrings;
    public String DESCRIPTION;
    public ArrayList<AbstractGameEffect> cardImageEffects = null;

    public boolean logGlobalMove = false;

    public BaseCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        initNameAndDescription(id);
    }

    public BaseCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, makeCardPath(id), cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        initNameAndDescription(id);
    }

    private static String makeCardPath(String id) {
        String[] split = id.split(":");
        String modId = split[0];
        String cardId = split[1];
        return CoreModPath.makeCardPath(cardId + ".png").replace(CoreModPath.getModId(), modId);
    }


    protected void upgradeTimes() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
        DisposableVariable.reset(this);
    }

    public void initNameAndDescription(String id) {
        cardStrings = languagePack.getCardStrings(id);
        DESCRIPTION = cardStrings.DESCRIPTION;
        this.name = cardStrings.NAME;
        this.rawDescription = DESCRIPTION;
        this.initializeTitle();
        this.initializeDescription();
    }


    public void setCardsToPreviewList(List<AbstractCard> cardsToPreviewList) {
        this.cardsToPreviewList = cardsToPreviewList;
        this.cardsToPreview = cardsToPreviewList.get(0);
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        changePreviewCardByTime();
        super.renderCardPreview(sb);
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        changePreviewCardByTime();
        super.renderCardPreviewInSingleView(sb);
    }

    private void changePreviewCardByTime() {
        if (cardsToPreviewList != null && (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard)) {
            if (previewCardsListLastChangeTime <= 0 || System.currentTimeMillis() - previewCardsListLastChangeTime > 1000) {
                previewCardsListLastChangeTime = System.currentTimeMillis();

                if (previewCardsListIndex >= cardsToPreviewList.size()) previewCardsListIndex = 0;
                this.cardsToPreview = cardsToPreviewList.get(previewCardsListIndex++);
                Log.logger.info("index = " + previewCardsListIndex);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (cardImageEffects != null) {
            Iterator<AbstractGameEffect> iterator = cardImageEffects.iterator();
            while (iterator.hasNext()) {
                AbstractGameEffect effect = iterator.next();
                effect.update();
                if (effect.isDone) {
                    iterator.remove();
                }
            }
        }
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        SpireSuper.call(sb);
        if (cardImageEffects != null) {
            Iterator<AbstractGameEffect> iterator = cardImageEffects.iterator();
            while (iterator.hasNext()) {
                AbstractGameEffect effect = iterator.next();
                effect.render(sb);
                if (effect.isDone) {
                    iterator.remove();
                }
            }
        }
    }

    public void addCardImageEffect(AbstractGameEffect effect) {
        if (!effect.isDone) {
            if (cardImageEffects == null) cardImageEffects = new ArrayList<>();
            cardImageEffects.add(effect);
        }
    }

    public void triggerOnTurnStartInBag() {

    }

    // 闪耀耗尽时
    public void triggerOnDisposed() {

    }

    // take from bag by any way
    public void triggerOnTakeFromBag() {

    }

    public void triggerOnGlobalMove() {

    }

    public void triggerOnTakeFromBagToHand() {

    }

    public void triggerOnPutInBag() {

    }

    public void triggerWhenMoveToDiscardPile() { // use this

    }

    public void triggerOnShuffleInfoDrawPile() {

    }

    public void triggerOnEndOfPlayerTurnInBag() {
    }

    public void triggerOnEndOfPlayerTurnInDrawPile() {
    }

    public void triggerOnEndOfPlayerTurnInDiscardPile() {
    }


    public void triggerOnAccretion() {

    }

    public void triggerOnBattleStart() {

    }

    public void onRelicChange() {
        // TODO
//        if (CardModifierManager.hasModifier(this, LockRelicCountModifier.ID)) {
//            initializeDescription();
//        }
    }

    public void onObtainThis() {
    }
}
