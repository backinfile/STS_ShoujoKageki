package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.BagField;

public class ToTheStage extends BaseCard {

    public static final String ID = ModInfo.makeID(ToTheStage.class.getSimpleName());

    public ToTheStage() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        CardGroup bag = BagField.bag.get(p);
        if (bag.size() == 1) {
            addToTop(new TakeCardFromBagAction());
        }
        if (bag.size() >= 2) {
            addToTop(new TakeCardFromBagAction());
            addToTop(new TakeCardFromBagAction());
            addToTop(new GainEnergyAction(2));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
