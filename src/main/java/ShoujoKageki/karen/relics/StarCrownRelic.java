package ShoujoKageki.karen.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.LockRelicAction;
import ShoujoKageki.relics.BaseRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StarCrownRelic extends BaseRelic {
    public static final String RAW_ID = StarCrownRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

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
