package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TowerOfPromise extends BaseCard {

    public static final String ID = ModInfo.makeID(TowerOfPromise.class.getSimpleName());

    public TowerOfPromise() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.color = CardColor.COLORLESS;
        selfRetain = true;
        exhaust = true;
        this.baseMagicNumber = this.magicNumber = 99;
        this.bagCardPreviewNumber = 99;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new TakeCardFromBagAction());
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
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
//            shuffleBackIntoDrawPile = true;
            exhaust = false;
            initializeDescription();
        }
    }
}
