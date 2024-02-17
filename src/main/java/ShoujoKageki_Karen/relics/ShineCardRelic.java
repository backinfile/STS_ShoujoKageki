package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

public class ShineCardRelic extends BaseRelic {
    public static final String RAW_ID = ShineCardRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public static final float Multiplier = 0.5f;

    public ShineCardRelic() {
        super(ID, RAW_ID, RelicTier.SHOP, LandingSound.FLAT);
    }


    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof ShopRoom) {
            this.flash();
            this.pulse = true;
        } else {
            this.pulse = false;
        }

    }
}
