package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NewSituation extends BaseCard {

    public static final String ID = KarenPath.makeID(NewSituation.class.getSimpleName());

    public NewSituation() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.cardsToPreview = new EatFood2();
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new MakeTempCardInBagAction(cardsToPreview.makeStatEquivalentCopy(), magicNumber, true, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }
}
