package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class WakeUp extends BaseCard {

    public static final String ID = ModInfo.makeID(WakeUp.class.getSimpleName());

    public WakeUp() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            addToBot(new BetterDrawPileToHandAction(2));
            return;
        }

        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new SelectDrawPile());
        choices.add(new SelectDiscardPile());
        choices.add(new SelectBagPile());
        this.addToBot(new ChooseOneAction(choices));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeBaseCost(0);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.bagCardPreviewSelectNumber = 2;
        }
    }
}
