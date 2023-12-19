package ShoujoKageki.cards.tool.blackMarket;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import ShoujoKageki.cards.tool.BlackToolCard;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class BlackAttack extends BlackToolCard {
    public static final String ID = makeID(BlackAttack.class.getSimpleName());

    public BlackAttack() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, 6);
        baseDamage = 20;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false)));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(3);
            initializeDescription();
        }
    }
}
