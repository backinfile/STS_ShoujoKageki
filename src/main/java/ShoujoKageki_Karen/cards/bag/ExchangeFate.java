package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.actions.bag.ReplaceHandAndBagAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;

public class ExchangeFate extends BaseCard {

    public static final String ID = KarenPath.makeID(ExchangeFate.class.getSimpleName());

    public ExchangeFate() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
        this.bagCardPreviewExchange = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ReplaceHandAndBagAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
//            upgradeBaseCost(0);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
