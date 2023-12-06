package thief.campfireOption;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import thief.ModInfo;
import thief.util.TextureLoader;

public class BlackMarketOption extends AbstractCampfireOption {
    private static final String RAW_ID = BlackMarketOption.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final int price = 20;

    public BlackMarketOption() {
        this.label = uiStrings.TEXT[0];
        this.description = uiStrings.TEXT[1] + uiStrings.TEXT[2] + price + uiStrings.TEXT[3];
        this.usable = AbstractDungeon.player.gold >= price;
        this.img = TextureLoader.getTexture(ModInfo.makeUIPath(RAW_ID + ".png"));
    }

    @Override
    public void useOption() {
        AbstractDungeon.player.loseGold(price);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    }
}
