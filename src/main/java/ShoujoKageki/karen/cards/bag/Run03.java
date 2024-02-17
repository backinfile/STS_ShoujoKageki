package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.PutToBagField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class Run03 extends BaseCard {
    public static final String ID = makeID(Run03.class.getSimpleName());

    public Run03() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 2;
        PutToBagField.putToBag.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
