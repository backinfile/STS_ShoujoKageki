package ShoujoKageki.actions;

import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.TokenCardField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainOrIgnoreAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(GainOrIgnoreAction.class.getSimpleName())).TEXT;

    private final AbstractCard gainCard;

    public GainOrIgnoreAction(AbstractCard gainCard) {
        this.gainCard = gainCard.makeCopy();
        if (gainCard.upgraded) {
            this.gainCard.upgrade();
        }
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.startDuration == this.duration) {
            CardGroup selectFrom = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            selectFrom.addToTop(gainCard);
            AbstractDungeon.gridSelectScreen.open(selectFrom, 1, true, TEXT[0]);

            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // selectFinish
            addToBot(new AddCardToDeckAction(gainCard));
            addToBot(new WaitAction(Settings.ACTION_DUR_FAST));

            addToBot(new MakeTempCardInHandAction(gainCard.makeSameInstanceOf(), false, true));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        }
        this.tickDuration();
    }
}
