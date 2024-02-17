package ShoujoKageki.karen.cards.relic;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.GainRelicPower;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KillAll extends BaseCard {

    public static final String ID = ModInfo.makeID(KillAll.class.getSimpleName());

    public KillAll() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 1;
        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerOnDisposed() {
        super.triggerOnDisposed();
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            GainRelicPower.addRandomRelicToReward(magicNumber);
        } else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GainRelicPower(magicNumber)));
        }
    }
}
