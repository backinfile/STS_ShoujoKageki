package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.BagDefendPower;

public class Letter extends BaseCard {

    public static final String ID = ModInfo.makeID(Letter.class.getSimpleName());

    public Letter() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new BagDefendPower(magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
