package ShoujoKageki.powers;


import ShoujoKageki.cards.tool.ToolCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki.ModInfo.makeID;

public class VoidPower extends BasePower {
    private static final String RAW_ID = VoidPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public VoidPower() {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, 0);

        // TODO DrawCardAction FastDrawCardAction DeckTopViewRelic CommonAttack.predict
    }
}
