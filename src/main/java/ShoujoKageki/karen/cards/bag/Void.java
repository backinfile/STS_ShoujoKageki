package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.actions.DestroyAllCardInDrawPileAction;
import ShoujoKageki.actions.bag.MoveCardToBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Void extends BaseCard {

    public static final String ID = ModInfo.makeID(Void.class.getSimpleName());

    public Void() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
//        GraveField.grave.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
//        addToBot(new ApplyPowerAction(p, p, new VoidPower()));
        BagField.bagReplace.set(p, true);
        addToBot(new DestroyAllCardInDrawPileAction());
        addToBot(new MoveCardToBagAction(BagField.getBag().group));
        addToBot(new ApplyBagPowerAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
