package thief.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.ModInfo;
import thief.actions.BagAction;
import thief.actions.TakeCardFromBagAction;
import thief.cards.BaseCard;
import thief.cards.tool.patch.BagField;
import thief.character.BasePlayer;

public class BagDown extends BaseCard {

    public static final String ID = ModInfo.makeID(BagDown.class.getSimpleName());

    public BagDown() {
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
