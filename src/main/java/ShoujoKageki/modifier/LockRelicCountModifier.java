package ShoujoKageki.modifier;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.LockRelicAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;


public class LockRelicCountModifier extends AbstractCardModifier {


    public static final String ID = ModInfo.makeID(LockRelicCountModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (AbstractDungeon.player == null) return rawDescription;
        int count = LockRelicAction.getCanLockRelicCount();
        if (count <= 1) {
            return rawDescription + TEXT[0] + count + TEXT[1];
        } else {
            return rawDescription + TEXT[0] + count + TEXT[2];
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new LockRelicCountModifier();
    }
}