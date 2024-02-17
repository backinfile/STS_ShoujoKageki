package ShoujoKageki_Karen.cards.strength;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.GainDexterityEquityToStrengthAction;
import ShoujoKageki.base.BaseCard;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class Run02 extends BaseCard {

    public static final String ID = KarenPath.makeID(Run02.class.getSimpleName());

    public Run02() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        isEthereal = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainDexterityEquityToStrengthAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
//            upgradeBaseCost(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
