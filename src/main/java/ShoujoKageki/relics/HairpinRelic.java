package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.patches.BagField;
import ShoujoKageki.effects.DeckTopEffect;
import ShoujoKageki.ui.DeckTopViewer;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HairpinRelic extends BaseRelic {
    public static final String RAW_ID = HairpinRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public HairpinRelic() {
        super(ID, RAW_ID, RelicTier.STARTER);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();

        int cardsNumInBag = BagField.bag.get(AbstractDungeon.player).size();
        if (cardsNumInBag > 0) {
            addToBot(new GainBlockAction(AbstractDungeon.player, cardsNumInBag));
        }
    }

}
