package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class NextStage extends BaseCard {

    public static final String ID = ModInfo.makeID(NextStage.class.getSimpleName());

    public NextStage() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE);
        this.color = CardColor.COLORLESS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        BagField.bagCostZero.set(p, true);
        addToBot(new ApplyBagPowerAction());
//        BagField.notifyBagPower();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
