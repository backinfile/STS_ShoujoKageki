package ShoujoKageki.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import ShoujoKageki.ModInfo;

public class TakeAllRewardCardsRelic extends BaseRelic {
    public static final String RAW_ID = TakeAllRewardCardsRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public TakeAllRewardCardsRelic() {
        super(ID, RAW_ID, RelicTier.UNCOMMON);
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter == -2) {
            this.grayscale = true;
            this.usedUp = true;
        }
    }

    @Override
    public void onTrigger() {
        this.flash();
        setCounter(-2);
    }

    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            if (this.counter == -2) {
                this.flash();
                this.setCounter(-1);
                this.grayscale = false;
                this.usedUp = false;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
