package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.campfireOption.BlackMarketOption;
import ShoujoKageki.campfireOption.ShineOption;
import ShoujoKageki.cards.bag.TowerOfPromise;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Girya;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.relics.Shovel;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;
import java.util.Iterator;

public class TapeRelic extends BaseRelic {
    public static final String RAW_ID = TapeRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public TapeRelic() {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
        setCounter(3);
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        super.addCampfireOption(options);
        if (counter > 0) {
            options.add(new ShineOption());
            flash();
        }
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        flash();
        --this.counter;

        if (this.counter <= 0) {
            this.setCounter(-2);
        }
    }

    @Override
    public boolean canSpawn() {
        if (AbstractDungeon.floorNum >= 48 && !Settings.isEndless) {
            return false;
        } else {
            int campfireRelicCount = 0;
            Iterator var2 = AbstractDungeon.player.relics.iterator();

            while(true) {
                AbstractRelic r;
                do {
                    if (!var2.hasNext()) {
                        return campfireRelicCount < 2;
                    }

                    r = (AbstractRelic)var2.next();
                } while(!(r instanceof PeacePipe) && !(r instanceof Shovel) && !(r instanceof Girya));

                ++campfireRelicCount;
            }
        }
    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
        }
    }

}
