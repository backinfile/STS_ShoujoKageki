package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.PutHandCardIntoBagAction;
import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhosPromise extends BaseCard {

    public static final String ID = ModInfo.makeID(WhosPromise.class.getSimpleName());

    public WhosPromise() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.baseMagicNumber = this.magicNumber = 1;
        this.bagCardPreviewNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new TakeCardFromBagAction(magicNumber));
        addToBot(new PutHandCardIntoBagAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            this.bagCardPreviewNumber = 2;
        }
    }
}
