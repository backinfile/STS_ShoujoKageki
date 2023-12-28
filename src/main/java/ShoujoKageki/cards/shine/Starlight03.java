package ShoujoKageki.cards.shine;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.Starlight03Power;
import ShoujoKageki.powers.StarlightPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class Starlight03 extends BaseCard {
    public static final String ID = makeID(Starlight03.class.getSimpleName());

    public Starlight03() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Starlight03Power()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
