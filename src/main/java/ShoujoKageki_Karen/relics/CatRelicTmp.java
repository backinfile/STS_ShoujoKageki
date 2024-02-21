package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import basemod.AutoAdd;

@AutoAdd.Ignore
public class CatRelicTmp extends BaseRelic {
    public static final String RAW_ID = CatRelicTmp.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public static final int BLOCK = 8;

    public CatRelicTmp() {
        super(ID, RAW_ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }
}