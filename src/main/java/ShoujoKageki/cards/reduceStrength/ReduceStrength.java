package ShoujoKageki.cards.reduceStrength;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.actions.ReduceStrengthAction;
import ShoujoKageki.cards.BaseCard;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class ReduceStrength extends BaseCard {
    public static final String ID = makeID(ReduceStrength.class.getSimpleName());

    public ReduceStrength() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ReduceStrengthAction(p, m, magicNumber));
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
