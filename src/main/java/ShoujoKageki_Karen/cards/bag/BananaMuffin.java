package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BananaMuffin extends BaseCard {

    public static final String ID = KarenPath.makeID(BananaMuffin.class.getSimpleName());


    public BananaMuffin() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, defaultSecondMagicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, defaultSecondMagicNumber)));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
//            upgradeDefaultSecondMagicNumber(1);
        }
    }
}
