package ShoujoKageki_Karen.reward;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.reward.patch.RewardPatch;
import ShoujoKageki_Karen.variables.DisposableVariable;
import basemod.BaseMod;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardSave;

import java.util.List;
import java.util.stream.Collectors;

public class ShineCardReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal(KarenPath.makeUIPath("ShineCardReward.png")));
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(KarenPath.makeID(ShineCardReward.class.getSimpleName()));

    public ShineCardReward(String saveString) {
        super(ICON, uiStrings.TEXT[0], RewardPatch.TypePatch.SHINE_CARD);
        AbstractCard card = CardLibrary.getCard(saveString);
        if (card != null) {
            init(card.makeCopy());
        }
    }

    public ShineCardReward() {
        super(ICON, uiStrings.TEXT[0], RewardPatch.TypePatch.SHINE_CARD);
        init(rollShineCard());
    }

    public static void register() {
        BaseMod.registerCustomReward(RewardPatch.TypePatch.SHINE_CARD, rewardSave -> new ShineCardReward(rewardSave.id), customReward -> {
            if (customReward.cards.isEmpty()) {
                return new RewardSave(customReward.type.toString(), "");
            }
            return new RewardSave(customReward.type.toString(), customReward.cards.get(0).cardID);
        });
    }

    public static void addShineCardRewardToRoom() {
        RewardItem rewardItem = new ShineCardReward();
        if (rewardItem.cards.isEmpty()) return;
        AbstractDungeon.getCurrRoom().addCardReward(rewardItem);
    }

    private void init(AbstractCard card) {
        this.cards.clear();
        if (card == null) return;

        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            relic.onPreviewObtainCard(card);
        }

        this.cards.add(card);
    }

    @Override
    public boolean claimReward() {
        if (this.cards.isEmpty()) return true;
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, uiStrings.TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }


    public static AbstractCard rollShineCard() {
        AbstractCard.CardRarity rarity = getRarity();
        List<AbstractCard> list;
        if (rarity.equals(AbstractCard.CardRarity.COMMON)) {
            list = AbstractDungeon.srcCommonCardPool.group;
        } else if (rarity.equals(AbstractCard.CardRarity.UNCOMMON)) {
            list = AbstractDungeon.srcUncommonCardPool.group;
        } else {
            list = AbstractDungeon.srcRareCardPool.group;
        }
        list = list.stream().filter(DisposableVariable::isDisposableCard).collect(Collectors.toList());
        if (list.isEmpty()) return null;
        int rnd = AbstractDungeon.cardRandomRng.random(list.size() - 1);
        return list.get(rnd).makeCopy();
    }

    public static AbstractCard.CardRarity getRarity() {
        int random = AbstractDungeon.cardRng.random(99);
        if (random < 3) return AbstractCard.CardRarity.RARE;
        if (random < 40) return AbstractCard.CardRarity.UNCOMMON;
        return AbstractCard.CardRarity.COMMON;
    }
}
