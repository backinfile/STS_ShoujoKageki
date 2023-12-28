package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.powers.ReproducePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StageReproduce extends BaseCard {

    public static final String ID = ModInfo.makeID(StageReproduce.class.getSimpleName());

    public StageReproduce() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
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
            upgradeBaseCost(1);
        }
    }
}
