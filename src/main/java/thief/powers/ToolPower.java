package thief.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thief.actions.TakeCardFromBagAction;
import thief.cards.bag.BagEnergy;
import thief.cards.patch.BagField;
import thief.cards.tool.ToolCard;
import thief.util.Utils2;

import static thief.ModInfo.makeID;

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
        if (card instanceof ToolCard) {
            return d + amount;
        }
        return d;
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        float b = super.modifyBlock(blockAmount, card);
        if (card instanceof ToolCard) {
            return b + amount;
        }
        return b;
    }
}