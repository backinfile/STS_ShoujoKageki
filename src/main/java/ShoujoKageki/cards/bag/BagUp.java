package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.PutHandCardIntoBagAction;
import ShoujoKageki.cards.BaseCard;

public class BagUp extends BaseCard {

    public static final String ID = ModInfo.makeID(BagUp.class.getSimpleName());

    public BagUp() {
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
