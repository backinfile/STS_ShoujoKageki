package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.BananaLunchPower;
import ShoujoKageki.powers.ReserveStrengthPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EatTogether extends BaseCard {

    public static final String ID = ModInfo.makeID(EatTogether.class.getSimpleName());

    public EatTogether() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 3;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new ReserveStrengthPower(defaultSecondMagicNumber)));
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
