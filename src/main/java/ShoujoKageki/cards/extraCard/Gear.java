package ShoujoKageki.cards.extraCard;

import ShoujoKageki.actions.CopyAllHandToBagAction;
import ShoujoKageki.actions.CopyHandCardToDeckAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.GearVideoEffect;
import ShoujoKageki.patches.TokenCardField;
import ShoujoKageki.relics.DeckTopRelic;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.SteroidPotion;

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
