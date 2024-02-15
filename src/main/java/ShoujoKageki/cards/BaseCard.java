package ShoujoKageki.cards;

import ShoujoKageki.modifier.LockRelicCountModifier;
import ShoujoKageki.variables.DisposableVariable;
import ShoujoKageki.variables.patch.DisposableField;
import ShoujoKageki.variables.patch.DisposableFieldUpgradePatch;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.List;
import java.util.function.Consumer;

import static ShoujoKageki.character.KarenCharacter.Enums.CardColor_Karen;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class BaseCard extends AbstractDefaultCard {

    public static final int DEFAULT_SHINE_CNT = 9;
    public static final int MEDIUM_SHINE_CNT = 6;
    public static final int LOW_SHINE_CNT = 3;

    public int bagCardPreviewNumber = 0; // 仅用于显示，表示即将从约定牌堆抽出几张
    public int bagCardPreviewSelectNumber = 0; // 仅用于显示，表示即将从约定牌堆选择几张
    public boolean bagCardPreviewExchange = false; // 仅用于显示，表示即将从约定牌堆交换

    private long previewCardsListLastChangeTime = -1;
    private int previewCardsListIndex = 0;
    private List<AbstractCard> cardsToPreviewList = null;
    public CardStrings cardStrings;
    public String DESCRIPTION;

    public boolean logGlobalMove = false;

    public BaseCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, CardColor_Karen, rarity, target);
        initNameAndDescription(id);
    }

    public BaseCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        initNameAndDescription(id);
    }

    public BaseCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, ModInfo.makeCardPath(id.split(":")[1] + ".png"), cost, languagePack.getCardStrings(id).DESCRIPTION, type, CardColor_Karen, rarity, target);
        initNameAndDescription(id);
    }

    public BaseCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, ModInfo.makeCardPath(id.split(":")[1] + ".png"), cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        initNameAndDescription(id);
    }

    protected void upgradeTimes() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
        DisposableVariable.reset(this);
    }

    private void initNameAndDescription(String id) {
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
        if (CardModifierManager.hasModifier(this, LockRelicCountModifier.ID)) {
            initializeDescription();
        }
    }
}
