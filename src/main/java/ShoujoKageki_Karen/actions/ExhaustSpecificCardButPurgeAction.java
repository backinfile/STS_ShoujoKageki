package ShoujoKageki_Karen.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class ExhaustSpecificCardButPurgeAction extends AbstractGameAction {
    private final AbstractCard targetCard;
    private final CardGroup group;
    private final float startingDuration;

    public ExhaustSpecificCardButPurgeAction(AbstractCard targetCard, CardGroup group) {
        this.targetCard = targetCard;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.EXHAUST;
        this.group = group;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
//            this.group.moveToExhaustPile(this.targetCard);
//            CardCrawlGame.dungeon.checkForPactAchievement();
//            this.targetCard.exhaustOnUseOnce = false;
//            this.targetCard.freeToPlayOnce = false;
            this.group.removeCard(targetCard);
            AbstractDungeon.effectList.add(new ExhaustCardEffect(targetCard));
        }

        this.tickDuration();
    }
}
