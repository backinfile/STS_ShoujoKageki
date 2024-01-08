package ShoujoKageki.powers;


import ShoujoKageki.actions.AquariumAction;
import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class AquariumPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(AquariumPower.class.getName());

    private static final String RAW_ID = AquariumPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public AquariumPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
//        addToBot(new TakeCardFromBagAction(amount));
        addToBot(new AquariumAction(amount));
        flash();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
