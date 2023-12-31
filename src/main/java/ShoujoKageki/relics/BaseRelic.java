package ShoujoKageki.relics;

import basemod.abstracts.CustomRelic;
import ShoujoKageki.ModInfo;
import ShoujoKageki.util.TextureLoader;

public abstract class BaseRelic extends CustomRelic {
    public BaseRelic(String id, String rawId, RelicTier tier) {
        super(id, TextureLoader.getTexture(ModInfo.makeRelicPath(rawId + ".png")),
                TextureLoader.getTexture(ModInfo.makeRelicPath(rawId + ".png")),
                tier, LandingSound.FLAT);
    }
    public BaseRelic(String id, String rawId, RelicTier tier, LandingSound landingSound) {
        super(id, TextureLoader.getTexture(ModInfo.makeRelicPath(rawId + ".png")),
                TextureLoader.getTexture(ModInfo.makeRelicPath(rawId + ".png")),
                tier, landingSound);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void triggerOnDead() {

    }
}
