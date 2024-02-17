package ShoujoKageki_Karen.cards.reduceStrength;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import ShoujoKageki_Karen.actions.ReduceStrengthAction;
import ShoujoKageki.base.BaseCard;

import static ShoujoKageki_Karen.KarenPath.makeID;

@AutoAdd.Ignore
public class ReduceStrength2 extends BaseCard {
    public static final String ID = makeID(ReduceStrength2.class.getSimpleName());

    public ReduceStrength2() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 14;
        baseMagicNumber = magicNumber = 14;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ReduceStrengthAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(4);
            initializeDescription();
        }
    }
}
