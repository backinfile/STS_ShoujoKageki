package thief.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;

import static thief.character.Thief.Enums.COLOR_GRAY;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class BaseCard extends AbstractDefaultCard {

	public BaseCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
		super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type,
				COLOR_GRAY, rarity, target);
	}
	public BaseCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
		super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type,
				color, rarity, target);
	}

	
}
