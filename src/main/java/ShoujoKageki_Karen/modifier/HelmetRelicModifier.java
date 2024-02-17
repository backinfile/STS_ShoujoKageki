package ShoujoKageki_Karen.modifier;

import ShoujoKageki_Karen.KarenPath;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class HelmetRelicModifier extends AbstractCardModifier {

    public HelmetRelicModifier() {
    }

    public static final String ID = KarenPath.makeID(HelmetRelicModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        card.updateCost(-9);
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        super.onApplyPowers(card);
        card.updateCost(-9);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HelmetRelicModifier();
    }
}
