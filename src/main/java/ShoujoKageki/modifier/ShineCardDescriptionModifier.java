package ShoujoKageki.modifier;

import ShoujoKageki.ModInfo;
import ShoujoKageki.variables.DisposableVariable;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ShineCardDescriptionModifier extends AbstractCardModifier {

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID("DisposableKeyword"));

    public static String[] TEXT = uiStrings.TEXT;

    public static final String ID = ModInfo.makeID(ShineCardDescriptionModifier.class.getSimpleName());

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return TEXT[0] + rawDescription + TEXT[1];
    }

    @Override
    public Color getGlow(AbstractCard card) {
        if (DisposableVariable.getValue(card) == 1) {
            return Color.FIREBRICK.cpy();
        }
        return super.getGlow(card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ShineCardDescriptionModifier();
    }
}
