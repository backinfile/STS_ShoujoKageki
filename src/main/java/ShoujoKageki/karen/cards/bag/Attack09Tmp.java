package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.PutToBagField;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class Attack09Tmp extends BaseCard {

    public static final String ID = ModInfo.makeID(Attack09Tmp.class.getSimpleName());

    public Attack09Tmp() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 12;
        PutToBagField.putToBag.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}
