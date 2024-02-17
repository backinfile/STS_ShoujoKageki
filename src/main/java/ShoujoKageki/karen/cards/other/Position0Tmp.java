package ShoujoKageki.karen.cards.other;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.Position0Power;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class Position0Tmp extends BaseCard {

    public static final String ID = ModInfo.makeID(Position0Tmp.class.getSimpleName());

    public Position0Tmp() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Position0Power(magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
//            isInnate = true;
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
