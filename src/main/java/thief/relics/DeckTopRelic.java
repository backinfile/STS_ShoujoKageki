package thief.relics;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import thief.Log;
import thief.campfireOption.BlackMarketOption;
import thief.effects.DeckTopEffect;
import thief.ui.DeckTopViewer;
import thief.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import thief.ModInfo;

import static thief.ModInfo.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DeckTopRelic extends BaseRelic {
    public static final String RAW_ID = DeckTopRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
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
