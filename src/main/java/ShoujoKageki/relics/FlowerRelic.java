package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

@SharedRelic
public class FlowerRelic extends BaseRelic {
    public static final String RAW_ID = FlowerRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public FlowerRelic() {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }
}
