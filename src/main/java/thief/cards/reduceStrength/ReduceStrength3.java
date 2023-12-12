package thief.cards.reduceStrength;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import thief.actions.ReduceStrengthAction;
import thief.cards.BaseCard;
import thief.powers.ReduceStrengthLimitPower;

import static thief.ModInfo.makeID;

public class ReduceStrength3 extends BaseCard {
    public static final String ID = makeID(ReduceStrength3.class.getSimpleName());

    public ReduceStrength3() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ReduceStrengthLimitPower(magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(-1);
        }
    }
}
