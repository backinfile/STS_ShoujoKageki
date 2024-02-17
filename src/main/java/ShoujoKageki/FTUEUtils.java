package ShoujoKageki;

import ShoujoKageki_Karen.KarenPath;
import basemod.abstracts.CustomMultiPageFtue;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.UIStrings;

public class FTUEUtils {
    public static final String COMBAT_TIP_KEY = ShoujokagekiPath.makeID("COMBAT_TIP");

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ShoujokagekiPath.makeID("FTUE"));
    private static final Texture[] TEXTURES = new Texture[]{
            ImageMaster.loadImage(KarenPath.makeUIPath("tip01.png")),
            ImageMaster.loadImage(KarenPath.makeUIPath("tip02.png")),
    };

    public static void openCombatTip() {
        if (!(Boolean) TipTracker.tips.get(COMBAT_TIP_KEY)) {
            AbstractDungeon.ftue = new CustomMultiPageFtue(TEXTURES, uiStrings.TEXT);
            TipTracker.neverShowAgain(COMBAT_TIP_KEY);
        }
    }
}
