package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.cards.BaseCard;

public class ExchangeFate extends BaseCard {

    public static final String ID = ModInfo.makeID(ExchangeFate.class.getSimpleName());

    public ExchangeFate() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
//        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new PutHandCardIntoBagAction(p, false, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
