package ShoujoKageki.powers;


import ShoujoKageki.cards.patches.field.PutToBagField;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class PutToBagPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(PutToBagPower.class.getName());

    private static final String RAW_ID = PutToBagPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);


    public PutToBagPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);

        if (!PutToBagField.putToBag.get(card)) {
            PutToBagField.putToBagOnce.set(card, true);
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
