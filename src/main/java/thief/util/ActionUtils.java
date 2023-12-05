package thief.util;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class ActionUtils {

	public static void addCopyCardToHand(AbstractPlayer player, AbstractCard card, int amount) {
		int handAmt = amount;
		int handSize = player.hand.size();
		if (amount + handSize > 10) {
			handAmt = 10 - handSize;
		}
		int discardAmount = amount - handAmt;

		for (int i = 0; i < handAmt; i++) {
			AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card));
		}

		if (discardAmount > 0) {
			addCopyCardToDiscard(player, card, discardAmount);
		}
	}

	public static void addCopyCardToDiscard(AbstractPlayer player, AbstractCard card, int amount) {
		for (int i = 0; i < amount; i++) {
			AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card));
		}
	}

	public static void addCopyCardToDrawPile(AbstractPlayer player, AbstractCard card, int amount) {
		for (int i = 0; i < amount; i++) {
			AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, (float) Settings.WIDTH / 2.0F,
					(float) Settings.HEIGHT / 2.0F, true, true, false));
		}
	}

	public static void resetBeforeMoving(AbstractCard card) {
		if (card == AbstractDungeon.player.hoveredCard) {
			AbstractDungeon.player.releaseCard();
		}
		AbstractDungeon.actionManager.removeFromQueue(card);
		card.unhover();
		card.untip();
		card.stopGlowing();
	}

}
