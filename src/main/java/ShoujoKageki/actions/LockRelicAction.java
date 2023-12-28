package ShoujoKageki.actions;

import ShoujoKageki.relics.LockRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class LockRelicAction extends AbstractGameAction {

    public static final ArrayList<String> WhiteList = new ArrayList<>();

    {
        WhiteList.add("loadout:");
    }

    public LockRelicAction() {
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPlayer p = AbstractDungeon.player;
            int positionToLock = getRelicPositionToLock();
            if (positionToLock < 0) {
                isDone = true;
                return;
            }

            AbstractRelic toLock = p.relics.get(positionToLock);
            AbstractRelic lockRelic = new LockRelic(toLock, positionToLock);
            lockRelic.instantObtain(p, positionToLock, false);
        }
        tickDuration();
    }

    public static boolean hasRelicToLock() {
        return getRelicPositionToLock() >= 0;
    }

    public static int getRelicPositionToLock() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = p.relics.size() - 1; i >= 0; i--) {
            AbstractRelic relic = p.relics.get(i);
            if (relic instanceof LockRelic) continue;
            if (WhiteList.stream().anyMatch(relic.relicId::contains)) continue;
            return i;
        }
        return -1;
    }
}
