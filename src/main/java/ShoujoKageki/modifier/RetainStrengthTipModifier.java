package ShoujoKageki.modifier;

import ShoujoKageki.ModInfo;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

public class RetainStrengthTipModifier extends AbstractCardModifier {

    public RetainStrengthTipModifier() {
    }

    public static final String ID = ModInfo.makeID(RetainStrengthTipModifier.class.getSimpleName());
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static String[] TEXT = uiStrings.TEXT;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }


    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        List<TooltipInfo> tooltipInfos = new ArrayList<>();
        tooltipInfos.add(new TooltipInfo(TEXT[0], TEXT[1]));
        return tooltipInfos;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainStrengthTipModifier();
    }
}
