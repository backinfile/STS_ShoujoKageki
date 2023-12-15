package thief.cards.bag;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.ModInfo;
import thief.actions.PutHandCardIntoBagAction;
import thief.cards.BaseCard;

public class BagDraw extends BaseCard {

    public static final String ID = ModInfo.makeID(BagDraw.class.getSimpleName());

    public BagDraw() {
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
