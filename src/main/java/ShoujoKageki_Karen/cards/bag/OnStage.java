package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.actions.bag.TakeCardFromBagAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;

public class OnStage extends BaseCard {

    public static final String ID = KarenPath.makeID(OnStage.class.getSimpleName());

    public OnStage() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 4;
        exhaust = true;
        this.bagCardPreviewNumber = 4;
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
