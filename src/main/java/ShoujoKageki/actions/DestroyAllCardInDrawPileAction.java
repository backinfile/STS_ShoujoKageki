package ShoujoKageki.actions;

import ShoujoKageki.effects.MoveCardToBagEffect;
import ShoujoKageki.effects.ShowAndHoldCardThenEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class DestroyAllCardInDrawPileAction extends AbstractGameAction {

    private boolean secondGen = false;

    public DestroyAllCardInDrawPileAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST * 2;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            CardGroup pile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            pile.group.addAll(AbstractDungeon.player.drawPile.group);
            for (AbstractCard card : pile.group) {
//                AbstractCard card = pile.group.get(i);
//                AbstractDungeon.effectList.add(new ShowAndHoldCardThenEffect(card));
//                addToTop(new ExhaustSpecificCardAction(card, pile));
                AbstractDungeon.player.drawPile.moveToExhaustPile(card);
                card.exhaustOnUseOnce = false;
                card.freeToPlayOnce = false;
            }
            AbstractDungeon.effectList.removeIf(e -> e instanceof ExhaustCardEffect);

            for (int i = 0; i < 4; i++) {
                addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
                genFog();
            }
            isDone = true;
        }
    }

    private void genFog() {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = 0; i < 90; ++i) {
                    AbstractDungeon.topLevelEffects.add(new ExhaustBlurEffect(MoveCardToBagEffect.DRAW_PILE_X, MoveCardToBagEffect.DRAW_PILE_Y));
                }
                CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
                this.isDone = true;
            }
        });
    }
}
