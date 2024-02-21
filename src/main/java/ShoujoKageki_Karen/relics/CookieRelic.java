package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CookieRelic extends BaseRelic {
    public static final String RAW_ID = CookieRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);

    public CookieRelic() {
        super(ID, RAW_ID, RelicTier.UNCOMMON);
        this.screenLess = false;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        CardGroup rewardCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        ArrayList<CardGroup> cardPools = new ArrayList<>();
        cardPools.add(AbstractDungeon.srcCommonCardPool);
        cardPools.add(AbstractDungeon.srcUncommonCardPool);
        cardPools.add(AbstractDungeon.srcRareCardPool);
        for (CardGroup cardPool : cardPools) {
            List<AbstractCard> cards = cardPool.group.stream().filter(DisposableVariable::isDisposableCard).collect(Collectors.toList());
            if (cards.isEmpty()) continue;
            int rnd = AbstractDungeon.cardRng.random(cards.size() - 1);
            rewardCards.group.add(cards.get(rnd).makeCopy());
        }
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(rewardCards, this.DESCRIPTIONS[1]);
    }

}