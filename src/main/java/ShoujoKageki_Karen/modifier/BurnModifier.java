package ShoujoKageki_Karen.modifier;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.actions.bag.ApplyBagPowerAction;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;


public class BurnModifier extends AbstractCardModifier {

    public BurnModifier() {
        this.priority = 1;
    }


    public static final String ID = KarenPath.makeID(BurnModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }


//    @Override
//    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (card.exhaust) return rawDescription;
//        return TEXT[0] + rawDescription + TEXT[1];
//    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        action.exhaustCard = true;
        AbstractDungeon.actionManager.addToBottom(new ApplyBagPowerAction());
    }

    @Override
    public Color getGlow(AbstractCard card) {
        return Color.FIREBRICK;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BurnModifier();
    }
}
