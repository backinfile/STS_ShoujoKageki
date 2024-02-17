package ShoujoKageki.karen.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.StarAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.patches.TokenCardField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Star extends BaseCard {

    public static final String ID = ModInfo.makeID(Star.class.getSimpleName());

    public Star() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new StarAction());
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                cardsToPreview = null;
                isDone = true;
            }
        });
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cardsToPreview = null;
        if (!super.canUse(p, m)) {
            return false;
        }
        ArrayList<AbstractCard> allShineCards = StarAction.getAllShineCardsWithoutBag();
        if (allShineCards.size() == 1) {
            this.cardsToPreview = allShineCards.get(0).makeCopy();
            TokenCardField.isToken.set(this.cardsToPreview, false);
            return true;
        } else if (allShineCards.isEmpty()) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
            return false;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (StarAction.getAllShineCardsWithoutBag().size() == 1) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            selfRetain = true;
//            upgradeBaseCost(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
