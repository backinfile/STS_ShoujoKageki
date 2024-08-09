package ShoujoKageki.powers;


import ShoujoKagekiCore.base.BasePower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static ShoujoKageki.ModInfo.makeID;

public class VoidPower extends BasePower {
    public static final String RAW_ID = VoidPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public VoidPower() {
        super(POWER_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, 0);

        // TODO DrawCardAction FastDrawCardAction DeckTopViewRelic CommonAttack.predict DrawCardAction.drawn
    }
}
