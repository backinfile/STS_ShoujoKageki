package ShoujoKageki.karen.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.relics.BaseRelic;
import ShoujoKageki.relics.SharedRelic;

@SharedRelic
public class FlowerRelic extends BaseRelic {
    public static final String RAW_ID = FlowerRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public FlowerRelic() {
        super(ID, RAW_ID, RelicTier.RARE, LandingSound.FLAT);
    }
}
