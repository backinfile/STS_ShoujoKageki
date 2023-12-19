package ShoujoKageki.cards.bag;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.PutHandCardIntoBagAction;
import ShoujoKageki.cards.BaseCard;

public class OurPromise extends BaseCard {

    public static final String ID = ModInfo.makeID(OurPromise.class.getSimpleName());

    public OurPromise() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new PutHandCardIntoBagAction(p, 2));
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
