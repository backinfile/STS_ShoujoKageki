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
        this.baseDamage = 14;
        this.baseMagicNumber = this.magicNumber = 10;
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

        if (Utils2.inBattlePhase()) {
            int counter = DisposableFieldCounterSavePatch.getDiffShineDisposedCount();
            if (counter != counterCache) {
                this.counterCache = counter;
                initializeDescription();
            }
        }
    }

    @Override
    public void applyPowers() {
        int counter = DisposableFieldCounterSavePatch.getDiffShineDisposedCount();
        int added = counter * baseMagicNumber;
        this.baseDamage += added;
        super.applyPowers();
        this.baseDamage -= added;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeDamage(4);
            upgradeMagicNumber(4);
            initializeDescription();
        }
    }
}
