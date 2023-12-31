package ShoujoKageki.cards.bag;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.AccretionField;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.cards.patches.field.PutToBagField;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class NeverRetreat02 extends BaseCard {
    public static final String ID = makeID(NeverRetreat02.class.getSimpleName());

    public NeverRetreat02() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        isMultiDamage = true;
        AccretionField.accretion.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public void triggerOnAccretion() {
        super.triggerOnAccretion();
        this.freeToPlayOnce = true;
    }
}
