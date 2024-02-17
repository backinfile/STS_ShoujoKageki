package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.actions.bag.PutHandCardIntoBagAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;

public class OurPromise extends BaseCard {

    public static final String ID = KarenPath.makeID(OurPromise.class.getSimpleName());

    public OurPromise() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new PutHandCardIntoBagAction(2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
