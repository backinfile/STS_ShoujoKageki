package ShoujoKageki_Nana.cards.stage;

import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki_Nana.NanaPath.makeID;


public class StageInstallation extends BaseCard {
    public static final String ID = makeID(StageInstallation.class.getSimpleName());

    public StageInstallation() {
        super(ID, 1, CardType.POWER, CardRarity.BASIC, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}