package ShoujoKageki.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.ReduceStrengthAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.modifier.CarryingGuiltModifier;
import ShoujoKageki.util.Utils2;
import ShoujoKageki.variables.DisposableVariable;
import ShoujoKageki.variables.patch.DisposableFieldCounterSavePatch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CarryingGuilt extends BaseCard {

    public static final String ID = ModInfo.makeID(CarryingGuilt.class.getSimpleName());

    public CarryingGuilt() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = this.magicNumber = 8;
//        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);

        CardModifierManager.addModifier(this, new CarryingGuiltModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    private int counterCache = 0;

    @Override
    public void update() {
        super.update();

        if (AbstractDungeon.isPlayerInDungeon()) {
            int counter = DisposableFieldCounterSavePatch.getDiffShineDisposedCount();
            if (counter != counterCache) {
                this.counterCache = counter;
                initializeDescription();
            }
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += DisposableFieldCounterSavePatch.getDiffShineDisposedCount() * baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += DisposableFieldCounterSavePatch.getDiffShineDisposedCount() * baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
