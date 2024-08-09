package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKagekiCore.shine.DisposableVariable;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.Iterator;

@AutoAdd.Ignore
public class MrWhiteRelic extends BaseRelic {
    public static final String RAW_ID = MrWhiteRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public static final int PILE_NUMBER = 30;

    public MrWhiteRelic() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        flash();
    }
}
