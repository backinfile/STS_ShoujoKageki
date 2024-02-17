package ShoujoKageki.karen.cards.relic;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.GainRelicPower;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Passion extends BaseCard {

    public static final String ID = ModInfo.makeID(Passion.class.getSimpleName());

    public Passion() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        DisposableVariable.setBaseValue(this, 1);
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GainRelicPower(1)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
