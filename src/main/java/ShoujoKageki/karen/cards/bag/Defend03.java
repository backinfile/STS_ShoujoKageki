package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend03 extends BaseCard {

    public static final String ID = ModInfo.makeID(Defend03.class.getSimpleName());

    public Defend03() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 3;
        this.cardsToPreview = new Attack07();
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
            upgradeBlock(3);
        }
    }
}
