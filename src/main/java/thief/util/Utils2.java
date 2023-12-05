package thief.util;

import java.util.StringJoiner;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

public class Utils2 {
	public static String getCardNames(CardGroup group, String sep) {
		StringJoiner sj = new StringJoiner(sep);
		for (AbstractCard card : group.group) {
			sj.add("[#FFFF87]" + card.name.trim().replace(" ", "[] [#FFFF87]") + "[]");
		}
		return sj.toString();
	}

	public static String getCardNames(CardGroup group, String sep, String pre, String suf) {
		StringJoiner sj = new StringJoiner(sep, pre, suf);
		for (AbstractCard card : group.group) {
			sj.add("[#FFFF87]" + card.name.trim().replace(" ", "[] [#FFFF87]") + "[]");
		}
		return sj.toString();
	}
	
	public static CardGroup getUnspecifiedCopy(CardGroup group) {
		CardGroup newGroup = new CardGroup(CardGroupType.UNSPECIFIED);
		for(AbstractCard card: group.group) {
			newGroup.addToTop(card);
		}
		return newGroup;
	}
}
