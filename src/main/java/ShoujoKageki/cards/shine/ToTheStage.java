package ShoujoKageki.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ToTheStage extends BaseCard {

    public static final String ID = ModInfo.makeID(ToTheStage.class.getSimpleName());

    public ToTheStage() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        DisposableVariable.setBaseValue(this, LOW_SHINE_CNT);
        this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        int value = DisposableVariable.getValue(this);
        if (value > 0) {
            addToBot(new GainEnergyAction(value));
        }
        if (magicNumber > 0) {
            addToBot(new DrawCardAction(magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
