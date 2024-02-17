package ShoujoKageki_Karen.campfireOption;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.effects.CampfireShineEffect;
import ShoujoKageki.util.TextureLoader;
import ShoujoKageki_Karen.variables.patch.DisposableField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class ShineOption extends AbstractCampfireOption {
    private static final String RAW_ID = ShineOption.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public ShineOption() {
        this.label = uiStrings.TEXT[0];
        this.description = uiStrings.TEXT[1];
        this.usable = true;
        this.img = TextureLoader.getTexture(KarenPath.makeUIPath(RAW_ID + ".png"));

        if (AbstractDungeon.player.masterDeck.isEmpty()) {
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
            if (DisposableField.disposable.get(card) > 0) {
                cardGroup.group.add(card);
            }
        }
        return cardGroup;
    }
}
