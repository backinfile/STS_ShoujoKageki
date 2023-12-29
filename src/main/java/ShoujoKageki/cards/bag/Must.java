package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Must extends BaseCard {

    public static final String ID = ModInfo.makeID(Must.class.getSimpleName());

    public Must() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.baseBlock = 7;
        this.cardsToPreview = new TowerOfPromise();
        this.baseMagicNumber = this.magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new MakeTempCardInHandAction(cardsToPreview.makeStatEquivalentCopy()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(5);
        }
    }
}
