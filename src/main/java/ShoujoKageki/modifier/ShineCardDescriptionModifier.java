package ShoujoKageki.modifier;

import ShoujoKageki.ModInfo;
import ShoujoKageki.powers.Starlight02Power;
import ShoujoKageki.variables.DisposableVariable;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        if (rawDescription.endsWith(uiStrings.TEXT[3])) {
            return uiStrings.TEXT[0] + rawDescription + uiStrings.TEXT[1];
        }
        return uiStrings.TEXT[0] + rawDescription + uiStrings.TEXT[2] + uiStrings.TEXT[1];
    }

    @Override
    public Color getGlow(AbstractCard card) {
        if (DisposableVariable.getValue(card) == 1) {
            return Color.FIREBRICK.cpy();
        }
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null) {
            if (player.hasPower(Starlight02Power.POWER_ID)) return Color.FIREBRICK.cpy();
        }
        return super.getGlow(card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ShineCardDescriptionModifier();
    }
}
