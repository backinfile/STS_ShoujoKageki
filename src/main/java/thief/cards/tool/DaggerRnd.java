package thief.cards.tool;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.ModInfo;
import thief.cards.BaseCard;
import thief.cards.tool.thief.Dagger;

public class DaggerRnd extends BaseCard {

    public static final String ID = ModInfo.makeID(DaggerRnd.class.getSimpleName());

    public DaggerRnd() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MakeTempCardInHandAction(Dagger.makeRndDagger(), 1, true));
        }
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
