package ShoujoKageki_Karen.cards.relic;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.LockRelicAction;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.modifier.LockRelicCountModifier;
import ShoujoKageki_Karen.relics.LockRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StarCrime extends BaseCard {

    public static final String ID = KarenPath.makeID(StarCrime.class.getSimpleName());

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
