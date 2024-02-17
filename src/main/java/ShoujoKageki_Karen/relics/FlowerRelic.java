package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import ShoujoKageki.base.SharedRelic;

@SharedRelic
public class FlowerRelic extends BaseRelic {
    public static final String RAW_ID = FlowerRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public FlowerRelic() {
        super(ID, RAW_ID, RelicTier.RARE, LandingSound.FLAT);
    }
}
