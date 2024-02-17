package ShoujoKageki_Karen.cards.starter;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fall extends BaseCard {

    public static final String ID = KarenPath.makeID(Fall.class.getSimpleName());

    public Fall() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        baseBlock = 8;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new PutHandCardIntoBagAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
