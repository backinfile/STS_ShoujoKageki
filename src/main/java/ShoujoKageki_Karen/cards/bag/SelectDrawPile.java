package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelectDrawPile extends BaseCard {

    public static final String ID = KarenPath.makeID(SelectDrawPile.class.getSimpleName());

    public SelectDrawPile() {
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
        addToBot(new BetterDrawPileToHandAction(2));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
