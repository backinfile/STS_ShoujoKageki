package ShoujoKageki.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.MoveAllShineCardsIntoBagAction;
import ShoujoKageki.actions.StarAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class StarGuide extends BaseCard {

    public static final String ID = ModInfo.makeID(StarGuide.class.getSimpleName());

    public StarGuide() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        isInnate = true;
        exhaust = true;
//        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GOLD.cpy(), true));
        this.addToBot(new WaitAction(Settings.ACTION_DUR_XFAST));
        this.addToBot(new MoveAllShineCardsIntoBagAction());
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (!StarAction.getAllShineCardsWithoutBag().isEmpty()) { // contains self
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
