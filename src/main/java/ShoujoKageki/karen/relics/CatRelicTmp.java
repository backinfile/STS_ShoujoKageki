package ShoujoKageki.karen.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.relics.BaseRelic;
import basemod.AutoAdd;

@AutoAdd.Ignore
public class CatRelicTmp extends BaseRelic {
    public static final String RAW_ID = CatRelicTmp.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public static final int BLOCK = 8;

    public CatRelicTmp() {
        super(ID, RAW_ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }
}
