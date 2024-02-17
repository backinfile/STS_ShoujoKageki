package ShoujoKageki_Karen.cards.starter;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MeetAgain extends BaseCard {

    public static final String ID = KarenPath.makeID(MeetAgain.class.getSimpleName());

    public MeetAgain() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 2;
        this.baseBlock = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new PutHandCardIntoBagAction(1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}
