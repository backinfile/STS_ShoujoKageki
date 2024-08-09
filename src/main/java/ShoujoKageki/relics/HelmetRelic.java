package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.modifier.HelmetRelicModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.List;

@SharedRelic
public class HelmetRelic extends BaseRelic {
    public static final String RAW_ID = HelmetRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    private boolean cardSelected;

    public HelmetRelic() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
        this.screenLess = false;
    }

    @Override
    public void onEquip() {
        List<AbstractCard> availableCards = getAvailableCards();
        if (!availableCards.isEmpty()) {
            this.cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            cardGroup.group.addAll(availableCards);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(cardGroup, 1, false, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD);
        }
    }

    @Override
    public void onSaveLoad() {
        super.onSaveLoad();
        cardSelected = true;
    }

    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            CardModifierManager.addModifier(card, new HelmetRelicModifier());
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));


            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
//            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
//            this.tips.clear();
//            this.tips.add(new PowerTip(this.name, this.description));
//            this.initializeTips();
        }
    }

    @Override
    public boolean canSpawn() {
        return !getAvailableCards().isEmpty();
    }

    public static List<AbstractCard> getAvailableCards() {
        List<AbstractCard> cards = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.costForTurn <= 0 || card.cost <= 0) {
                continue;
            }
            if (CardModifierManager.hasModifier(card, HelmetRelicModifier.ID)) continue;
            cards.add(card);
        }
        return cards;
    }
}
