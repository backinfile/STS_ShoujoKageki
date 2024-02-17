package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import ShoujoKageki_Karen.campfireOption.ShineOption;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class TapeRelic extends BaseRelic {
    public static final String RAW_ID = TapeRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public TapeRelic() {
        super(ID, RAW_ID, RelicTier.RARE, LandingSound.FLAT);
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

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
        }
    }

}
