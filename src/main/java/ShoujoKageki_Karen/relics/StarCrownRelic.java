package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.LockRelicAction;
import ShoujoKageki.base.BaseRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StarCrownRelic extends BaseRelic {
    public static final String RAW_ID = StarCrownRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public static final int BLOCK = 8;

    public StarCrownRelic() {
        super(ID, RAW_ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public int getStokeWidth() {
        return 3;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        addToBot(new LockRelicAction(2));
    }
}
