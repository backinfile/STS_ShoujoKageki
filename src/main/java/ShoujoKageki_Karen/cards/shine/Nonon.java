package ShoujoKageki_Karen.cards.shine;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.ReduceStrengthAction;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Nonon extends BaseCard {

    public static final String ID = KarenPath.makeID(Nonon.class.getSimpleName());

    public Nonon() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 12;
        isMultiDamage = true;
        this.baseMagicNumber = this.magicNumber = 12;
        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ReduceStrengthAction(p, m, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(4);
        }
    }
}
