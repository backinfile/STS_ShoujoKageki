package ShoujoKageki.base;

import ShoujoKageki_Karen.character.KarenCharacter;
import ShoujoKageki_Karen.modifier.LockRelicCountModifier;
import ShoujoKageki_Karen.variables.DisposableVariable;
import ShoujoKageki_Nana.NanaPath;
import ShoujoKageki_Nana.character.NanaCharacter;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.Log;
import ShoujoKageki_Karen.KarenPath;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.List;

import static ShoujoKageki_Karen.character.KarenCharacter.Enums.CardColor_Karen;
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

    public BaseCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, getImg(id), cost, languagePack.getCardStrings(id).DESCRIPTION, type, getCardColor(id), rarity, target);
        initNameAndDescription(id);
    }

    private static AbstractCard.CardColor getCardColor(String id) {
        String[] split = id.split(":");
        if (split[0].equals(KarenPath.Name)) {
            return CardColor_Karen;
        } else {
            return NanaCharacter.Enums.CardColor_Nana;
        }
    }

    private static String getImg(String id) {
        String[] split = id.split(":");
        if (split[0].equals(KarenPath.Name)) {
            return KarenPath.makeCardPath(split[1] + ".png");
        } else {
            return KarenPath.makeCardPath("Defend.png");
//            return NanaPath.makeCardPath(split[1] + ".png");
        }
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
