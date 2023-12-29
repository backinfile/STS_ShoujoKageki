package ShoujoKageki.cards.strength;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.GainDexterityEquityToStrengthAction;
import ShoujoKageki.actions.GainCardOrIgnoreAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Run02 extends BaseCard {

    public static final String ID = ModInfo.makeID(Run02.class.getSimpleName());

    public Run02() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
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
            upgradeBaseCost(0);
        }
    }
}
