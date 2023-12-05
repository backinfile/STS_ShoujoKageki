package thief.cards.starter;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import thief.ModInfo;
import thief.actions.BagAction;
import thief.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

public class BagBottom extends BaseCard {

    public static final String ID = ModInfo.makeID(BagBottom.class.getSimpleName());
    public static final String IMG = ModInfo.makeCardPath("Attack.png");


    public BagBottom() {
        super(ID, IMG, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 2)));
        addToBot(new BagAction(magicNumber));
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
