package ShoujoKageki.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;

import java.util.List;

import static ShoujoKageki.character.KarenCharacter.Enums.CardColor_Karen;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class BaseCard extends AbstractDefaultCard {

    private long previewCardsListLastChangeTime = -1;
    private int previewCardsListIndex = 0;
    private List<AbstractCard> cardsToPreviewList = null;

    public BaseCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, CardColor_Karen, rarity, target);
    }

    public BaseCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
    }

    public BaseCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, ModInfo.makeCardPath(id.split(":")[1] + ".png"), cost, languagePack.getCardStrings(id).DESCRIPTION, type, CardColor_Karen, rarity, target);
    }

    public BaseCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, ModInfo.makeCardPath(id.split(":")[1] + ".png"), cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

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
}
