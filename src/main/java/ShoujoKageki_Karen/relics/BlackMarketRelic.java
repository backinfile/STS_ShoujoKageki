package ShoujoKageki_Karen.relics;

import ShoujoKageki.base.BaseRelic;
import ShoujoKageki_Karen.variables.DisposableVariable;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ShoujoKageki_Karen.KarenPath;

@AutoAdd.Ignore
public class BlackMarketRelic extends BaseRelic {
    public static final String RAW_ID = BlackMarketRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public BlackMarketRelic() {
        super(ID, RAW_ID, RelicTier.SPECIAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (DisposableVariable.isDisposableCard(drawnCard)) {
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
