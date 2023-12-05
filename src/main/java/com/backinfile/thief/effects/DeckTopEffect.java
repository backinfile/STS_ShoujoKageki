package com.backinfile.thief.effects;

import com.backinfile.thief.relics.DeckTopRelic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DeckTopEffect extends AbstractGameEffect {

    @Override
    public void update() {
        if (DeckTopRelic.deckTopViewer != null) {
            DeckTopRelic.deckTopViewer.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (DeckTopRelic.deckTopViewer != null) {
            DeckTopRelic.deckTopViewer.render(sb);
        }
    }

    @Override
    public void dispose() {

    }
}
