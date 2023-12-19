package ShoujoKageki.cards.tool;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.MakeTempCardInBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.tool.dagger.Dagger;

public class DaggerRnd extends BaseCard {

    public static final String ID = ModInfo.makeID(DaggerRnd.class.getSimpleName());

    public DaggerRnd() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        setCardsToPreviewList(Dagger.ALL_DAGGER_CARDS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MakeTempCardInHandAction(Dagger.makeRndDagger(), 1, true));
        }
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MakeTempCardInBagAction(Dagger.makeRndDagger(), 1, true, false));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
