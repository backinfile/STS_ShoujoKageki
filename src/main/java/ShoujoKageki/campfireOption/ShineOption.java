package ShoujoKageki.campfireOption;

import ShoujoKageki.ModInfo;
import ShoujoKageki.effects.CampfireShineEffect;
import ShoujoKageki.screen.BlackMarketScreen;
import ShoujoKageki.util.TextureLoader;
import ShoujoKageki.variables.patch.DisposableField;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;

public class ShineOption extends AbstractCampfireOption {
    private static final String RAW_ID = ShineOption.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public ShineOption() {
        this.label = uiStrings.TEXT[0];
        this.description = uiStrings.TEXT[1];
        this.usable = true;
        this.img = TextureLoader.getTexture(ModInfo.makeUIPath(RAW_ID + ".png"));

        if (getAllShineCards().isEmpty()) {
            this.usable = false;
        }
    }

    @Override
    public void useOption() {

        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfireShineEffect(this));
        }
    }


    public static CardGroup getAllShineCards() {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (DisposableField.DisposableFields.disposable.get(card) > 0) {
                cardGroup.group.add(card);
            }
        }
        return cardGroup;
    }
}