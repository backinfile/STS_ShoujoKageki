package ShoujoKageki.modifier;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.util.Utils2;
import ShoujoKageki.variables.DisposableVariable;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class TotalShineDescriptionModifier extends AbstractCardModifier {


    public static final String ID = ModInfo.makeID(TotalShineDescriptionModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        Log.logger.info("========== on modify");
        if (!Utils2.inRoom()) return rawDescription;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            return rawDescription + TEXT[0] + DisposableVariable.getTotalShineValueInBattle() + TEXT[1];
        } else {
            return rawDescription + TEXT[0] + DisposableVariable.getTotalShineValueInDeck() + TEXT[1];
        }
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        super.onApplyPowers(card);
        card.initializeDescription();
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TotalShineDescriptionModifier();
    }
}