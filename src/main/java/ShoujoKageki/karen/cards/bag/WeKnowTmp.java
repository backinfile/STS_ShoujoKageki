package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.NextTurnDrawBagPower;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class WeKnowTmp extends BaseCard {
    public static final String ID = makeID(WeKnowTmp.class.getSimpleName());

    public WeKnowTmp() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 15;
        this.baseMagicNumber = this.magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p, p, new NextTurnDrawBagPower(magicNumber)));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }
}
