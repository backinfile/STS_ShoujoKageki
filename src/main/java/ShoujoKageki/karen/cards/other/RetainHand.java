package ShoujoKageki.karen.cards.other;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class RetainHand extends BaseCard {

    public static final String ID = ModInfo.makeID(RetainHand.class.getSimpleName());

    public RetainHand() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        this.color = CardColor.COLORLESS;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        super.onChoseThisOption();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, magicNumber))); // 手牌
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
