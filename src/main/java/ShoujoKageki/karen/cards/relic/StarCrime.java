package ShoujoKageki.karen.cards.relic;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.LockRelicAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.modifier.LockRelicCountModifier;
import ShoujoKageki.karen.relics.LockRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StarCrime extends BaseCard {

    public static final String ID = ModInfo.makeID(StarCrime.class.getSimpleName());

    public StarCrime() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 3;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 2;
        CardModifierManager.addModifier(this, new LockRelicCountModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LockRelicAction(defaultSecondMagicNumber));
        addToBot(new DrawCardAction(magicNumber));
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
