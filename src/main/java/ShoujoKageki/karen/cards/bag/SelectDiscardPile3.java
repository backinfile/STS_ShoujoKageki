package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.AwakePotionAction;
import ShoujoKageki.cards.BaseCard;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelectDiscardPile3 extends BaseCard {

    public static final String ID = ModInfo.makeID(SelectDiscardPile3.class.getSimpleName());

    public SelectDiscardPile3() {
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
        addToBot(new BetterDiscardPileToHandAction(number, true));
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
