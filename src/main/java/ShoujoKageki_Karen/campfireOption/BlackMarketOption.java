package ShoujoKageki_Karen.campfireOption;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.screen.BlackMarketScreen;
import ShoujoKageki.util.TextureLoader;

public class BlackMarketOption extends AbstractCampfireOption {
    private static final String RAW_ID = BlackMarketOption.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final int price = 20;

    public BlackMarketOption() {
        this.label = uiStrings.TEXT[0];
        this.description = uiStrings.TEXT[1];
        this.usable = true;
        this.img = TextureLoader.getTexture(KarenPath.makeUIPath(RAW_ID + ".png"));

        BlackMarketScreen blackMarketScreen = (BlackMarketScreen) BaseMod.getCustomScreen(BlackMarketScreen.Enum.BLACK_MARKET);
        blackMarketScreen.init();
    }

    @Override
    public void useOption() {
        BaseMod.openCustomScreen(BlackMarketScreen.Enum.BLACK_MARKET);
    }
}
