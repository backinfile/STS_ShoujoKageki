package ShoujoKageki.cards.other;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.modifier.RetainStrengthTipModifier;
import ShoujoKageki.powers.ReserveStrengthPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import java.util.ArrayList;

public class OldPlace2 extends BaseCard {

    public static final String ID = ModInfo.makeID(OldPlace2.class.getSimpleName());

    public OldPlace2() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 1;


        CardModifierManager.addModifier(this, new RetainStrengthTipModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));

        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new RetainEnergy());
        choices.add(new RetainHand());
        choices.add(new RetainBlock());
        choices.add(new RetainStrength());
        if (upgraded) {
            for (AbstractCard card : choices) card.upgrade();
        }
        this.addToBot(new ChooseOneAction(choices));

//        addToBot(new ApplyPowerAction(p, p, new ConservePower(p, magicNumber))); // 能量
//        addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, magicNumber))); // 手牌
//        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, magicNumber))); // 格挡
//        addToBot(new ApplyPowerAction(p, p, new ReserveStrengthPower(magicNumber))); // 力量
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
