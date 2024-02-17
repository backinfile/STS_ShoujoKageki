package ShoujoKageki.karen.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.SelectBagCardToHandAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Dance extends BaseCard {

    public static final String ID = ModInfo.makeID(Dance.class.getSimpleName());

    public Dance() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        this.baseMagicNumber = this.magicNumber = 2;
        this.bagCardPreviewSelectNumber = this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new SelectBagCardToHandAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(2);
            this.bagCardPreviewSelectNumber = this.magicNumber;
        }
    }
}
