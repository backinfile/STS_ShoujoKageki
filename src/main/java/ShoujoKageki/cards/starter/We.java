package ShoujoKageki.cards.starter;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.PutDeckTopCardIntoBagAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class We extends BaseCard {

    public static final String ID = ModInfo.makeID(We.class.getSimpleName());

    public We() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 2)));
        addToBot(new PutDeckTopCardIntoBagAction());
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
