package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import ShoujoKageki.effects.BurnEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Burn extends BaseCard {

    public static final String ID = ModInfo.makeID(Burn.class.getSimpleName());

    public Burn() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
//        addToBot(new ApplyPowerAction(p, p, new BurnPower()));
        BagField.bagBurn.set(p, true);
        BagField.bagUpgrade.set(p, true);
        addToBot(new ApplyBagPowerAction());
        AbstractDungeon.effectList.add(new BurnEffect());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
