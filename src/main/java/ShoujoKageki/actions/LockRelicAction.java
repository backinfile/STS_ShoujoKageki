package ShoujoKageki.actions;

import ShoujoKageki.Log;
import ShoujoKageki.patches.OnRelicChangePatch;
import ShoujoKageki.karen.relics.CatRelicTmp;
import ShoujoKageki.karen.relics.LockRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class LockRelicAction extends AbstractGameAction {

    public static final ArrayList<String> WhiteList = new ArrayList<>();

    static {
        WhiteList.add("loadout:".toLowerCase());
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

                if (toLock instanceof CatRelicTmp) {
                    toLock.flash();
                    addToTop(new GainBlockAction(p, CatRelicTmp.BLOCK));
                    continue;
                }

                AbstractRelic lockRelic = new LockRelic(toLock, positionToLock);
                lockRelic.instantObtain(p, positionToLock, false);
                OnRelicChangePatch.publishOnRelicChange();
            }
        }
        tickDuration();
    }


    public static boolean hasRelicToLock(int number) {
        return getCanLockRelicCount() >= number;
    }

    public static int getCanLockRelicCount() {
        int count = 0;
        int endPosition = -1;
        while (true) {
            endPosition = getRelicPositionToLock(endPosition);
            if (endPosition < 0) break;
            count++;
        }
        return count;
    }

    public static int getRelicPositionToLock(int endPosition) {
        AbstractPlayer p = AbstractDungeon.player;
        if (endPosition < 0) endPosition = p.relics.size();
        for (int i = endPosition - 1; i >= 0; i--) {
            AbstractRelic relic = p.relics.get(i);
            if (relic instanceof LockRelic) continue;
            if (relic.tier == AbstractRelic.RelicTier.BOSS) continue;
            if (WhiteList.stream().anyMatch(relic.relicId.toLowerCase()::contains)) continue;
            Log.logger.debug("========= got {}", relic.relicId);
            return i;
        }
        return -1;
    }
}
