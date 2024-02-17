package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.SelectBagCardToHandAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelectBagPile extends BaseCard {

    public static final String ID = ModInfo.makeID(SelectBagPile.class.getSimpleName());

    public SelectBagPile() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        this.color = CardColor.COLORLESS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        super.onChoseThisOption();
        addToBot(new SelectBagCardToHandAction(2));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
