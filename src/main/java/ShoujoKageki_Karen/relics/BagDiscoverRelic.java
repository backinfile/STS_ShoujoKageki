package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import basemod.AutoAdd;


@AutoAdd.Ignore
public class BagDiscoverRelic extends BaseRelic {
    public static final String RAW_ID = BagDiscoverRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public static final int TAKE_NUM = 5;

    public BagDiscoverRelic() {
        super(ID, RAW_ID, RelicTier.COMMON);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
