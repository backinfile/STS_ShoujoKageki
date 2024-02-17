package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.cards.patches.field.AccretionField;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class StageIsWaitingTmp extends BaseCard {

    public static final String ID = KarenPath.makeID(StageIsWaitingTmp.class.getSimpleName());

    public StageIsWaitingTmp() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
        AccretionField.accretion.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnTakeFromBagToHand() {
        super.triggerOnTakeFromBagToHand();
        addToBot(new GainEnergyAction(magicNumber));
        flash();
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if (BagField.isChangeToDrawPile()) {
            addToBot(new GainEnergyAction(magicNumber));
            flash();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
