package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.AccretionField;
import ShoujoKageki.variables.patch.DisposableFieldCounterSavePatch;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class SeriousStrike extends BaseCard {

    public static final String ID = ModInfo.makeID(SeriousStrike.class.getSimpleName());


    private int addedDamage = 0;

    public SeriousStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 12;
//        AccretionField.accretion.set(this, true);
        this.baseMagicNumber = this.magicNumber = 4;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += addedDamage;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += addedDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void triggerOnAccretion() {
        super.triggerOnAccretion();
        this.addedDamage += magicNumber;
        applyPowers();
        initializeDescription();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInBag() {
        super.triggerOnEndOfPlayerTurnInBag();
        this.addedDamage += magicNumber;
        applyPowers();
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
