package ShoujoKageki.relics;

import basemod.abstracts.CustomRelic;
import ShoujoKageki.ModInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class BaseRelic extends ShoujoKagekiCore.base.BaseRelic {
    public BaseRelic(String id, RelicTier tier) {
        super(id, tier);
    }

    public BaseRelic(String id, RelicTier tier, LandingSound landingSound) {
        super(id, tier, landingSound);
    }
}
