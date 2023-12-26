package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.BagField;

public class RevueDuet extends BaseCard {

    public static final String ID = ModInfo.makeID(RevueDuet.class.getSimpleName());

    public RevueDuet() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        CardGroup bag = BagField.bag.get(p);
        if (bag.size() >= magicNumber) {
            addToTop(new TakeCardFromBagAction(magicNumber));
            addToTop(new GainEnergyAction(2));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (BagField.bag.get(p).size() < magicNumber) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
