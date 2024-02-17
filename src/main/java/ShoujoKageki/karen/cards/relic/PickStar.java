package ShoujoKageki.karen.cards.relic;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.LockRelicAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.modifier.LockRelicCountModifier;
import ShoujoKageki.karen.relics.LockRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PickStar extends BaseCard {

    public static final String ID = ModInfo.makeID(PickStar.class.getSimpleName());

    public PickStar() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = 2;
        CardModifierManager.addModifier(this, new LockRelicCountModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LockRelicAction(defaultSecondMagicNumber));
        addToBot(new GainEnergyAction(magicNumber));
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
            upgradeDefaultSecondMagicNumber(-1);
        }
    }
}
