package ShoujoKageki_Karen.relics.patch;

import ShoujoKageki_Karen.actions.bag.MoveCardToBagAction;
import ShoujoKageki_Karen.relics.MrWhiteRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;

import java.util.ArrayList;

public class MrWhiteRelicPatch {

    @SpirePatch2(
            clz = CardGroup.class,
            method = "initializeDeck",
            paramtypez = CardGroup.class
    )
    public static class OnDead {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "copy"
        )
        public static void Insert(CardGroup __instance, CardGroup copy) {
            if (copy.size() <= MrWhiteRelic.PILE_NUMBER) return;

            AbstractRelic relic = AbstractDungeon.player.getRelic(MrWhiteRelic.ID);
            if (relic == null) return;
            relic.flash();

            ArrayList<AbstractCard> moveToBagCards = new ArrayList<>();

            int takeCnt = copy.size() - MrWhiteRelic.PILE_NUMBER;
            for (int i = 0; i < takeCnt; i++) {
                moveToBagCards.add(copy.getNCardFromTop(i));
            }
//            Collections.reverse(moveToBagCards);
            for (AbstractCard card : moveToBagCards) copy.group.remove(card);
            AbstractDungeon.actionManager.addToBottom(new MoveCardToBagAction(moveToBagCards));
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "shuffle");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
