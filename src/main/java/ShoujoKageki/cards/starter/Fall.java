package ShoujoKageki.cards.starter;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fall extends BaseCard {

    public static final String ID = ModInfo.makeID(Fall.class.getSimpleName());

    public Fall() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        baseBlock = 8;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new PutHandCardIntoBagAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
