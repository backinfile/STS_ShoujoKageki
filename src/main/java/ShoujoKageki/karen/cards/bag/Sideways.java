package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sideways extends BaseCard {

    public static final String ID = ModInfo.makeID(Sideways.class.getSimpleName());

    public Sideways() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 6;
        exhaust = true;
        this.color = CardColor.COLORLESS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(6);
        }
    }
}
