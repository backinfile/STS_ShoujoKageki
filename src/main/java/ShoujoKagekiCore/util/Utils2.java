package ShoujoKagekiCore.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

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

    public static boolean inBattlePhase() {
        if (!AbstractDungeon.isPlayerInDungeon() || AbstractDungeon.player == null || AbstractDungeon.player.hand == null) {
            return false;
        }
        if (AbstractDungeon.getCurrMapNode() == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }
        return true;
    }

    public static boolean inRoom() {
        if (!AbstractDungeon.isPlayerInDungeon() || AbstractDungeon.player == null || AbstractDungeon.player.hand == null) {
            return false;
        }
        if (AbstractDungeon.getCurrMapNode() == null || AbstractDungeon.getCurrRoom() == null) {
            return false;
        }
        return true;
    }

    public static AbstractCard getMasterDeckEquivalent(AbstractCard playingCard) {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            c = (AbstractCard) var1.next();
        } while (!c.uuid.equals(playingCard.uuid));

        return c;
    }


    public static final ArrayList<Function<AbstractPlayer, List<AbstractCard>>> bagProviders = new ArrayList<>();
    public static ArrayList<AbstractCard> getAllCardsInCombat(AbstractPlayer player) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(player.drawPile.group);
        cards.addAll(player.discardPile.group);
        cards.addAll(player.exhaustPile.group);
        cards.addAll(player.hand.group);
        for (Function<AbstractPlayer, List<AbstractCard>> provider : bagProviders) {
            List<AbstractCard> bagCards = provider.apply(player);
            if (bagCards != null) cards.addAll(bagCards);
        }
        return cards;
    }
}
