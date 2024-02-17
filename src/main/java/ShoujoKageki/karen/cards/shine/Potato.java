package ShoujoKageki.karen.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Potato extends BaseCard {

    public static final String ID = ModInfo.makeID(Potato.class.getSimpleName());

    public Potato() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 4;
        DisposableVariable.setBaseValue(this, LOW_SHINE_CNT);
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}
