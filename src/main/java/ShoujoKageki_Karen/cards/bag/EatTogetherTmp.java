package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.base.BaseCard;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class EatTogetherTmp extends BaseCard {

    public static final String ID = KarenPath.makeID(EatTogetherTmp.class.getSimpleName());

    public EatTogetherTmp() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseBlock = 7;
        this.cardsToPreview = new EatFood2();
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void triggerOnBattleStart() {
        super.triggerOnBattleStart();
        addToBot(new MakeTempCardInBagAction(this.cardsToPreview.makeCopy(), magicNumber, true, false));
        flash();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }
}
