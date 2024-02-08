package ShoujoKageki.cards.tool;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static ShoujoKageki.ModInfo.makeID;

public class Ready extends BaseCard {
    public static final String ID = makeID(Ready.class.getSimpleName());


    public Ready() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
        DisposableVariable.setBaseValue(this, MEDIUM_SHINE_CNT);
        this.isInnate = true;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new DrawCardAction(defaultSecondMagicNumber));
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
