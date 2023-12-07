package thief.campfireOption;

import basemod.BaseMod;
import basemod.abstracts.CustomScreen;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import thief.ModInfo;
import thief.screen.BlackMarketScreen;
import thief.util.TextureLoader;

public class BlackMarketOption extends AbstractCampfireOption {
    private static final String RAW_ID = BlackMarketOption.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final int price = 20;

    public BlackMarketOption() {
        this.label = uiStrings.TEXT[0];
        this.description = uiStrings.TEXT[1];
        this.usable = true;
        this.img = TextureLoader.getTexture(ModInfo.makeUIPath(RAW_ID + ".png"));

        BlackMarketScreen blackMarketScreen = (BlackMarketScreen) BaseMod.getCustomScreen(BlackMarketScreen.Enum.BLACK_MARKET);
        blackMarketScreen.init();
    }

    @Override
    public void useOption() {
        BaseMod.openCustomScreen(BlackMarketScreen.Enum.BLACK_MARKET);
    }
}
