package ShoujoKageki_Karen.cards.strength;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.powers.Position0Power;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Position0 extends BaseCard {

    public static final String ID = KarenPath.makeID(Position0.class.getSimpleName());

    public Position0() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Position0Power(magicNumber)));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeMagicNumber(1);
//            isInnate = true;
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
            upgradeBaseCost(2);
        }
    }
}
