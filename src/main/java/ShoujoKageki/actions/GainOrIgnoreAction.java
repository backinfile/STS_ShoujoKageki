package ShoujoKageki.actions;

import ShoujoKageki.ModInfo;
import ShoujoKageki.patches.TokenCardField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
    private boolean retrieveCard = false;

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
            AbstractDungeon.cardRewardScreen.customCombatOpen(selectFrom.group, TEXT[0], true);
            tickDuration();
            return;
        }

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
            AbstractCard targetCard = AbstractDungeon.cardRewardScreen.discoveryCard;
            AbstractDungeon.cardRewardScreen.discoveryCard = null;
            TokenCardField.isToken.set(targetCard, false);
            addToBot(new MakeTempCardInHandAction(targetCard, false, true));
            addToBot(new AddCardToDeckAction(targetCard.makeSameInstanceOf()));
            isDone = true;
        }

        this.tickDuration();
    }
}
