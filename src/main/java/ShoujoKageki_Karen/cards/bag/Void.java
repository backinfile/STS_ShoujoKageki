package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.ApplyBagPowerAction;
import ShoujoKageki_Karen.actions.DestroyAllCardInDrawPileAction;
import ShoujoKageki_Karen.actions.bag.MoveCardToBagAction;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Void extends BaseCard {

    public static final String ID = KarenPath.makeID(Void.class.getSimpleName());

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
