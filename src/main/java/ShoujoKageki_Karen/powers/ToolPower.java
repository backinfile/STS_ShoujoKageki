package ShoujoKageki_Karen.powers;


import ShoujoKageki.base.BasePower;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class ToolPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(ToolPower.class.getName());

    private static final String RAW_ID = ToolPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public ToolPower(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        float d = super.atDamageFinalGive(damage, type, card);
        if (DisposableVariable.isDisposableCard(card)) {
            return d + amount;
        }
        return d;
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        float b = super.modifyBlock(blockAmount, card);
        if (DisposableVariable.isDisposableCard(card)) {
            return b + amount;
        }
        return b;
    }
}
