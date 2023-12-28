package ShoujoKageki.cards.relic;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.ArrogantPower;
import ShoujoKageki.powers.PassionPower;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Arrogant extends BaseCard {

    public static final String ID = ModInfo.makeID(Arrogant.class.getSimpleName());

    public Arrogant() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ArrogantPower()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}
