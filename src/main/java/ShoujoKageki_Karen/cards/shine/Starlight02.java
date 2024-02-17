package ShoujoKageki_Karen.cards.shine;

import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.powers.Starlight02Power;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class Starlight02 extends BaseCard {
    public static final String ID = makeID(Starlight02.class.getSimpleName());

    public Starlight02() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Starlight02Power(magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
