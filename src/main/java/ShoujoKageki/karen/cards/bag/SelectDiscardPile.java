package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelectDiscardPile extends BaseCard {

    public static final String ID = ModInfo.makeID(SelectDiscardPile.class.getSimpleName());

    public SelectDiscardPile() {
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
        addToBot(new BetterDiscardPileToHandAction(2));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
