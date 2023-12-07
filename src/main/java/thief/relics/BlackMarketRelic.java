package thief.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thief.ModInfo;
import thief.cards.tool.BlackToolCard;
import thief.effects.DeckTopEffect;
import thief.ui.DeckTopViewer;

public class BlackMarketRelic extends BaseRelic {
    public static final String RAW_ID = BlackMarketRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public BlackMarketRelic() {
        super(ID, RAW_ID, RelicTier.SPECIAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (drawnCard instanceof BlackToolCard) {
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
