package ShoujoKageki_Karen.modifier;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.variables.patch.DisposableFieldCounterSavePatch;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;


public class CarryingGuiltModifier extends AbstractCardModifier {

    public CarryingGuiltModifier() {
    }


    public static final String ID = KarenPath.makeID(CarryingGuiltModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (!AbstractDungeon.isPlayerInDungeon()) return rawDescription;
        int counter = DisposableFieldCounterSavePatch.getDiffShineDisposedCount();
        return rawDescription + TEXT[0] + counter + TEXT[1];
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CarryingGuiltModifier();
    }
}
