package ShoujoKageki.util;

import java.util.StringJoiner;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

public class Utils2 {
    public static AbstractCard makeCardCopyOnlyWithUpgrade(AbstractCard source) {
        AbstractCard copy = source.makeCopy();
        if (source.upgraded) {
            for (int i = 0; i < Math.max(1, source.timesUpgraded); i++) {
                copy.upgrade();
            }
        }
        return copy;
    }


    public static String getCardNames(CardGroup group, String sep, boolean reverse) {
        StringJoiner sj = new StringJoiner(sep);
        if (reverse) {
            for (int i = group.size() - 1; i >= 0; i--) {
                AbstractCard card = group.group.get(i);
                sj.add("[#FFFF87]" + card.name.trim().replace(" ", "[] [#FFFF87]") + "[]");
            }
        } else {
            for (AbstractCard card : group.group) {
                sj.add("[#FFFF87]" + card.name.trim().replace(" ", "[] [#FFFF87]") + "[]");
            }
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
        for (AbstractCard card : group.group) {
            newGroup.addToTop(card);
        }
        return newGroup;
    }
}
