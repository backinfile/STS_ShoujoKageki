package ShoujoKageki.karen.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.relics.BaseRelic;
import basemod.AutoAdd;

@AutoAdd.Ignore
public class MrWhiteRelic extends BaseRelic {
    public static final String RAW_ID = MrWhiteRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public static final int PILE_NUMBER = 30;

    public MrWhiteRelic() {
        super(ID, RAW_ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        flash();
    }
}
