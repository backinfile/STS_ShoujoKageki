package ShoujoKageki_Karen.relics;

import ShoujoKageki.base.BaseRelic;
import ShoujoKageki_Karen.effects.DeckTopEffect;
import ShoujoKageki_Karen.ui.DeckTopViewer;
import ShoujoKageki_Karen.KarenPath;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@AutoAdd.Ignore
public class DeckTopRelic extends BaseRelic {
    public static final String RAW_ID = DeckTopRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);
    public static DeckTopViewer deckTopViewer = null;

    public DeckTopRelic() {
        super(ID, RAW_ID, RelicTier.STARTER);
    }

    @Override
    public void atBattleStart() {
//        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.beginLongPulse();

        if (deckTopViewer == null) {
            deckTopViewer = new DeckTopViewer();
        }
        deckTopViewer.open(true);

        AbstractDungeon.effectsQueue.add(new DeckTopEffect());
//        long count = AbstractDungeon.effectsQueue.stream().filter(e -> e instanceof DeckTopEffect).count();
//        Log.logger.info("============effect " + count);

//        addToBot(new MakeTempCardInBagAction(Dagger.makeRndDagger(), 15, true));
    }

    @Override
    public void onVictory() {
        deckTopViewer.hide();
        this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
