package ShoujoKageki.powers;


import ShoujoKageki.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.cards.bag.EatFood2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class BananaLunchPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(BananaLunchPower.class.getName());

    private static final String RAW_ID = BananaLunchPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public BananaLunchPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);

        if (isPlayer) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.flash();
                this.addToBot(new MakeTempCardInBagAction(new EatFood2(), this.amount, true, false));
            }
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
