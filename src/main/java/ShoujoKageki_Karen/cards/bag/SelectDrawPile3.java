package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.AwakePotionAction;
import ShoujoKageki.base.BaseCard;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelectDrawPile3 extends BaseCard {

    public static final String ID = KarenPath.makeID(SelectDrawPile3.class.getSimpleName());

    public SelectDrawPile3() {
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
        int number = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        addToBot(new BetterDrawPileToHandAction(number, true));
        AwakePotionAction.continueAction();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
