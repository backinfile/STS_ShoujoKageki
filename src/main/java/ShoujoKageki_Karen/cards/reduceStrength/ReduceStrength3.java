package ShoujoKageki_Karen.cards.reduceStrength;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.powers.ReduceStrengthLimitPower;

import static ShoujoKageki_Karen.KarenPath.makeID;

@AutoAdd.Ignore
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
