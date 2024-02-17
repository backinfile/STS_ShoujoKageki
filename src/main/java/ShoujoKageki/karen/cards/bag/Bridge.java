package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.BridgeAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bridge extends BaseCard {

    public static final String ID = ModInfo.makeID(Bridge.class.getSimpleName());

    public Bridge() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new BridgeAction(p, this.upgraded, this.freeToPlayOnce, this.energyOnUse, this.magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        if (BagField.hasCardsInBagToDraw()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
