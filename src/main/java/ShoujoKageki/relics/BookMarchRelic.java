package ShoujoKageki.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import ShoujoKageki.ModInfo;

public class BookMarchRelic extends BaseRelic {
    public static final String RAW_ID = BookMarchRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);

    public BookMarchRelic() {
        super(ID, RAW_ID, RelicTier.UNCOMMON);
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter == -2) {
            this.grayscale = true;
            this.usedUp = true;
        } else {
            this.grayscale = false;
            this.usedUp = false;
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
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
