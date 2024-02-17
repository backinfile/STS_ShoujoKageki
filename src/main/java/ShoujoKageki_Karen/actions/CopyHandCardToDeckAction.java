package ShoujoKageki_Karen.actions;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.util.Utils2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CopyHandCardToDeckAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(KarenPath.makeID(CopyHandCardToDeckAction.class.getSimpleName())).TEXT;

    private List<AbstractCard> cardsCannotCopy = null;
    private final Predicate<AbstractCard> predicate;

    private static ArrayList<AbstractCard> cardsOrderSave = new ArrayList<>();

    public CopyHandCardToDeckAction(int amount, Predicate<AbstractCard> predicate) {
        this.amount = amount;
        this.predicate = predicate;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;

        if (this.startDuration == this.duration) {
            cardsCannotCopy = p.hand.group.stream().filter(c -> !predicate.test(c)).collect(Collectors.toList());
            int canCopyCnt = p.hand.size() - cardsCannotCopy.size();
            if (canCopyCnt <= 0) {
                isDone = true;
                return;
            }

            saveHandOrder();
            p.hand.group.removeAll(cardsCannotCopy);
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, true, true);
            p.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                addToBot(new AddCardToDeckAction(Utils2.makeCardCopyOnlyWithUpgrade(card)));
                p.hand.group.add(card);
            }
            if (cardsCannotCopy != null) {
                p.hand.group.addAll(cardsCannotCopy);
            }
            recoverHandOrder();
            p.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            isDone = true;
            return;
        }
        this.tickDuration();
    }

    private void saveHandOrder() {
        if (!cardsCannotCopy.isEmpty()) {
            cardsOrderSave.addAll(AbstractDungeon.player.hand.group);
        }
    }

    private void recoverHandOrder() {
        if (cardsOrderSave.isEmpty()) return;
        AbstractPlayer p = AbstractDungeon.player;


        Map<AbstractCard, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < cardsOrderSave.size(); i++) {
            orderMap.put(cardsOrderSave.get(i), i);
        }
        for (int i = 0; i < p.hand.group.size(); i++) {
            AbstractCard card = p.hand.group.get(i);
            if (!orderMap.containsKey(card)) orderMap.put(card, i + 99);
        }

        p.hand.group.sort(Comparator.comparingInt(orderMap::get));
    }

}
