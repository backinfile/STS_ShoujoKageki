package ShoujoKageki.cards.shine;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class RealTimeAttack extends BaseCard {
    public static final String ID = makeID(RealTimeAttack.class.getSimpleName());

    public RealTimeAttack() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 0;
        DisposableVariable.setBaseValue(this, MEDIUM_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }

    @Override
    public void update() {
        super.update();

        long hour = (System.currentTimeMillis() / (1000 * 60 * 60) + 8) % 24;
        if (this.baseDamage != hour) {
            this.baseDamage = (int) hour;
        }
    }
}
