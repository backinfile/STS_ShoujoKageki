package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class NoHesitate extends BaseCard {

    public static final String ID = ModInfo.makeID(NoHesitate.class.getSimpleName());


    public NoHesitate() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 2;
        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        if (!BagField.isChangeToDrawPile(false)) choices.add(new SelectDrawPile2());
        choices.add(new SelectDiscardPile2());
        choices.add(new SelectHand2());
        for (AbstractCard card : choices) {
            if (this.upgraded) card.upgrade();
        }
        this.addToBot(new ChooseOneAction(choices));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
