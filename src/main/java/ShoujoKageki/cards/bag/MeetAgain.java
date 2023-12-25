package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.BagField;

public class MeetAgain extends BaseCard {

    public static final String ID = ModInfo.makeID(MeetAgain.class.getSimpleName());

    public MeetAgain() {
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        if (BagField.bag.get(p).size() < 2) return false;
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
