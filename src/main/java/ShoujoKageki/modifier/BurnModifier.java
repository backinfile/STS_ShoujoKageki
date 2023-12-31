package ShoujoKageki.modifier;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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


    public static final String ID = ModInfo.makeID(BurnModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card.exhaust) return rawDescription;
        return TEXT[0] + rawDescription + TEXT[1];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        action.exhaustCard = true;
        AbstractDungeon.actionManager.addToBottom(new ApplyBagPowerAction());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BurnModifier();
    }
}
