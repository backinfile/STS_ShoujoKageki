package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;

public class OnStage extends BaseCard {

    public static final String ID = ModInfo.makeID(OnStage.class.getSimpleName());

    public OnStage() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new TakeCardFromBagAction(magicNumber));
        addToBot(new GainEnergyAction(2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
