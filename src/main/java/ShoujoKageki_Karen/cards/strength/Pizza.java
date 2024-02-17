package ShoujoKageki_Karen.cards.strength;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.ReduceStrengthAction;
import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Pizza extends BaseCard {

    public static final String ID = KarenPath.makeID(Pizza.class.getSimpleName());

    public Pizza() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ReduceStrengthAction(p, defaultSecondMagicNumber, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDefaultSecondMagicNumber(1);
        }
    }
}
