package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.variables.DisposableVariable;
import ShoujoKageki.variables.patch.DisposableField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CookieRelic extends BaseRelic {
    public static final String RAW_ID = CookieRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);

    public CookieRelic() {
        super(ID, RAW_ID, RelicTier.UNCOMMON);
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
            rewardCards.group.add(cards.get(rnd));
        }
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(rewardCards, this.DESCRIPTIONS[1]);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
