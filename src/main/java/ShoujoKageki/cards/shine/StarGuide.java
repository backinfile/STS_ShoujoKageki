package ShoujoKageki.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.MoveAllShineCardsIntoBagAction;
import ShoujoKageki.actions.StarAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StarGuide extends BaseCard {

    public static final String ID = ModInfo.makeID(StarGuide.class.getSimpleName());

    public StarGuide() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        isInnate = true;
        exhaust = true;
        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MoveAllShineCardsIntoBagAction());
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (StarAction.getAllShineCardsWithoutBag().size() > 1) { // contains self
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
//            selfRetain = true;
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
