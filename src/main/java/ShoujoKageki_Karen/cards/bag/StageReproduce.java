package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StageReproduce extends BaseCard {

    public static final String ID = KarenPath.makeID(StageReproduce.class.getSimpleName());

    public StageReproduce() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        this.cardsToPreview = new Continue();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        BagField.bagInfinite.set(p, true);
//        addToBot(new ApplyPowerAction(p, p, new ReproducePower()));
        addToBot(new ApplyBagPowerAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}
