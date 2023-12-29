package ShoujoKageki.reward;

import ShoujoKageki.ModInfo;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.List;

public class ShineCardReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal("[pathtotexturefile]"));
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(ShineCardReward.class.getSimpleName()));

    public ShineCardReward(List<AbstractCard> cards) {
        super(ICON, uiStrings.TEXT[0], RewardType.CARD);
    }

    @Override
    public boolean claimReward() {
        return false;
    }

}
