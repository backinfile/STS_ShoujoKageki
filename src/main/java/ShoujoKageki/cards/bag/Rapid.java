package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.BackToBackAction;
import ShoujoKageki.actions.DiscardFromBagAction;
import ShoujoKageki.actions.bag.SelectBagCardToHandAction;
import ShoujoKageki.cards.BaseCard;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Rapid extends BaseCard {

    public static final String ID = ModInfo.makeID(Rapid.class.getSimpleName());

    public Rapid() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        this.bagCardPreviewSelectNumber = this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectBagCardToHandAction(magicNumber));
        addToBot(new DiscardFromBagAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
            this.bagCardPreviewSelectNumber = this.magicNumber;
        }
    }
}
