package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.campfireOption.ShineOption;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;
import java.util.Iterator;

public class FrogWaterCupRelic extends BaseRelic {
    public static final String RAW_ID = FrogWaterCupRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public FrogWaterCupRelic() {
        super(ID, RAW_ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void onEquip() {
        Iterator var1 = AbstractDungeon.combatRewardScreen.rewards.iterator();

        while (true) {
            RewardItem reward;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                reward = (RewardItem) var1.next();
            } while (reward.cards == null);

            Iterator var3 = reward.cards.iterator();

            while (var3.hasNext()) {
                AbstractCard c = (AbstractCard) var3.next();
                this.onPreviewObtainCard(c);
            }
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (DisposableVariable.isDisposableCard(c)) {
            DisposableVariable.setValue(c, DisposableVariable.getValue(c) + 1);
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}
