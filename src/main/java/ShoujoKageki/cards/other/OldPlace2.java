package ShoujoKageki.cards.other;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.ReduceStrengthAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.bag.SelectBagPile;
import ShoujoKageki.cards.bag.SelectDiscardPile;
import ShoujoKageki.cards.bag.SelectDrawPile;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class OldPlace2 extends BaseCard {

    public static final String ID = ModInfo.makeID(OldPlace2.class.getSimpleName());

    public OldPlace2() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseBlock = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        if (!upgraded) {
            ArrayList<AbstractCard> choices = new ArrayList<>();
            choices.add(new RetainEnegy());
            choices.add(new RetainHand());
            choices.add(new RetainBlock());
            this.addToBot(new ChooseOneAction(choices));
        } else {
            addToBot(new ApplyPowerAction(p, p, new ConservePower(p, 1))); // 能量
            addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, 1))); // 手牌
            addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1))); // 格挡
        }

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
