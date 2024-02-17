package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.powers.StageIsWaitingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StageIsWaiting extends BaseCard {

    public static final String ID = KarenPath.makeID(StageIsWaiting.class.getSimpleName());

    public StageIsWaiting() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
//        AccretionField.accretion.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new StageIsWaitingPower(magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeMagicNumber(1);
            upgradeBaseCost(1);
        }
    }
}
