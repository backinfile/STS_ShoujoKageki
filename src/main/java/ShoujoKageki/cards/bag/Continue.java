package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Continue extends BaseCard {

    public static final String ID = ModInfo.makeID(Continue.class.getSimpleName());

    public Continue() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.baseBlock = 3;
        this.color = CardColor.COLORLESS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new GainBlockAction(p, p, baseBlock));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(2);
        }
    }
}
