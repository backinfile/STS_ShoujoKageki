package ShoujoKageki.cards.bag;

import ShoujoKageki.actions.bag.ReplaceHandAndBagAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.cards.BaseCard;

public class ExchangeFate extends BaseCard {

    public static final String ID = ModInfo.makeID(ExchangeFate.class.getSimpleName());

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
