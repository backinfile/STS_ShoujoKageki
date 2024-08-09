package ShoujoKageki.cards.extraCard;

import ShoujoKageki.actions.CopyAllHandToBagAction;
import ShoujoKageki.actions.RunAction;
import ShoujoKageki.actions.TrueWaitAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.CardImageVideoEffect;
import ShoujoKagekiCore.token.TokenCardField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class Gear extends BaseCard {
    public static final String ID = makeID(Gear.class.getSimpleName());

    public Gear() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.topLevelEffectsQueue.add(new GearVideoEffect());
//        addToBot(new WaitAction(Settings.ACTION_DUR_LONG));
//        addToBot(new TrueWaitAction(0.5f));
        addToBot(new RunAction(() -> addCardImageEffect(new CardImageVideoEffect(this, "Gear_card.webm"))));
        addToBot(new TrueWaitAction(0.5f));
        addToBot(new CopyAllHandToBagAction());
//        addToBot(new CopyHandCardToDeckAction(1, this::canCopy));
    }

//    @Override
//    public void triggerOnGlowCheck() {
//        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
//        AbstractPlayer p = AbstractDungeon.player;
//        if (p.hand.group.stream().anyMatch(card -> card != this && canCopy(card))) {
//            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
//        }
//    }

    private boolean canCopy(AbstractCard card) {
        return TokenCardField.isToken.get(card);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
//            selfRetain = true;
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
