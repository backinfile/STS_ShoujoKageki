package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.PutToBagField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Stretching extends BaseCard {

    public static final String ID = ModInfo.makeID(Stretching.class.getSimpleName());

    public Stretching() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 14;
        PutToBagField.putToBag.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }
}
