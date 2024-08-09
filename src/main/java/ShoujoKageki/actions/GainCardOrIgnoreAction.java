package ShoujoKageki.actions;

import ShoujoKageki.ModInfo;
import ShoujoKagekiCore.token.TokenCardField;
import ShoujoKageki.util.Utils2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GainCardOrIgnoreAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(GainCardOrIgnoreAction.class.getSimpleName())).TEXT;

    private final List<AbstractCard> gainCards = new ArrayList<>();
    private boolean retrieveCard = false;
    private boolean duplicate = false;

    private boolean reduceCost = false;

    public GainCardOrIgnoreAction(AbstractCard gainCard) {
        this.gainCards.add(Utils2.makeCardCopyOnlyWithUpgrade(gainCard));
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public GainCardOrIgnoreAction(List<AbstractCard> gainCards, boolean duplicate, boolean reduceCost) {
        this.gainCards.addAll(gainCards.stream().map(Utils2::makeCardCopyOnlyWithUpgrade).collect(Collectors.toList()));
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.duplicate = duplicate;
        this.reduceCost = reduceCost;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.startDuration == this.duration) {
            CardGroup selectFrom = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            selectFrom.group.addAll(gainCards);
            for (AbstractCard card : selectFrom.group) {
                for (AbstractRelic relic : AbstractDungeon.player.relics) relic.onPreviewObtainCard(card);
            }
            AbstractDungeon.cardRewardScreen.customCombatOpen(selectFrom.group, TEXT[0], true);
            tickDuration();
            return;
        }

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
            AbstractCard targetCard = AbstractDungeon.cardRewardScreen.discoveryCard;
            AbstractDungeon.cardRewardScreen.discoveryCard = null;
            TokenCardField.isToken.set(targetCard, false);

            addToBot(new AddCardToDeckAction(targetCard.makeSameInstanceOf()));
            if (reduceCost) targetCard.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(targetCard, false, true));


            if (duplicate) {
                AbstractCard card = Utils2.makeCardCopyOnlyWithUpgrade(targetCard);
                addToBot(new AddCardToDeckAction(card.makeSameInstanceOf()));
                if (reduceCost) card.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(card, false, true));
            }
            isDone = true;
        }

        this.tickDuration();
    }
}
