package thief.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thief.ModInfo;
import thief.cards.tool.BlackToolCard;

public class BagDiscoverRelic extends BaseRelic {
    public static final String RAW_ID = BagDiscoverRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public static final int TAKE_NUM = 5;

    public BagDiscoverRelic() {
        super(ID, RAW_ID, RelicTier.COMMON);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
