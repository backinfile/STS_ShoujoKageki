package thief.relics;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
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

public class DeckTopRelic extends CustomRelic {

    public static final String ID = ModInfo.makeID(DeckTopRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("glasses_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("glasses_relic.png"));

    public static DeckTopViewer deckTopViewer = null;

    public DeckTopRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.beginLongPulse();

        if (deckTopViewer == null) {
            deckTopViewer = new DeckTopViewer();
        }
        deckTopViewer.open(true);

        if (AbstractDungeon.effectList.stream().noneMatch(e -> e instanceof DeckTopEffect)) {
            AbstractDungeon.effectsQueue.add(new DeckTopEffect());
        }
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
