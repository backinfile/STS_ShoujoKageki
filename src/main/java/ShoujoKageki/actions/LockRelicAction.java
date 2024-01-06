package ShoujoKageki.actions;

import ShoujoKageki.Log;
import ShoujoKageki.relics.CatRelic;
import ShoujoKageki.relics.LockRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LockRelicAction extends AbstractGameAction {

    public static final ArrayList<String> WhiteList = new ArrayList<>();

    {
        WhiteList.add("loadout:");
    }

    public LockRelicAction(int amount) {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPlayer p = AbstractDungeon.player;
            for (int i = 0; i < amount; i++) {
                int positionToLock = getRelicPositionToLock(-1);
                if (positionToLock < 0) {
                    break;
                }

                AbstractRelic toLock = p.relics.get(positionToLock);

                if (toLock instanceof CatRelic) {
                    toLock.flash();
                    addToTop(new GainBlockAction(p, CatRelic.BLOCK));
                    continue;
                }

                AbstractRelic lockRelic = new LockRelic(toLock, positionToLock);
                lockRelic.instantObtain(p, positionToLock, false);
            }
        }
        tickDuration();
    }


    public static boolean hasRelicToLock(int number) {
        int endPosition = -1;
        for (int i = 0; i < number; i++) {
            endPosition = getRelicPositionToLock(endPosition);
            if (endPosition < 0) return false;
            Log.logger.info("===find " + endPosition);
        }
        return true;
    }

    public static int getRelicPositionToLock(int endPosition) {
        AbstractPlayer p = AbstractDungeon.player;
        if (endPosition < 0) endPosition = p.relics.size();
        for (int i = endPosition - 1; i >= 0; i--) {
            AbstractRelic relic = p.relics.get(i);
            if (relic instanceof LockRelic) continue;
            if (WhiteList.stream().anyMatch(relic.relicId::contains)) continue;
            return i;
        }
        return -1;
    }
}
