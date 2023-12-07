package thief.cards.tool.thief;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.cards.tool.ThiefToolCard;

import static thief.ModInfo.makeID;

public class Dagger5 extends ThiefToolCard {
    public static final String ID = makeID(Dagger5.class.getSimpleName());

    public Dagger5() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, 1);
        baseDamage = 5;
        isMultiDamage = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}
