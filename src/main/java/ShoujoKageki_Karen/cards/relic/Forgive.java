package ShoujoKageki_Karen.cards.relic;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.LockRelicAction;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.modifier.LockRelicCountModifier;
import ShoujoKageki_Karen.relics.LockRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Forgive extends BaseCard {

    public static final String ID = KarenPath.makeID(Forgive.class.getSimpleName());

    public Forgive() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 21;
        isMultiDamage = true;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 1;
        CardModifierManager.addModifier(this, new LockRelicCountModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LockRelicAction(defaultSecondMagicNumber));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!LockRelicAction.hasRelicToLock(defaultSecondMagicNumber)) {
            cantUseMessage = LockRelic.DESCRIPTIONS[2];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(7);
        }
    }
}