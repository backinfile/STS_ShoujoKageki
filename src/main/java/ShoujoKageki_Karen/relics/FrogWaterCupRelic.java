package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FrogWaterCupRelic extends BaseRelic {
    public static final String RAW_ID = FrogWaterCupRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);

    public FrogWaterCupRelic() {
        super(ID, RAW_ID, RelicTier.COMMON, LandingSound.FLAT);
    }

//    public void onEquip() {
//        Iterator var1 = AbstractDungeon.combatRewardScreen.rewards.iterator();
//
//        while (true) {
//            RewardItem reward;
//            do {
//                if (!var1.hasNext()) {
//                    return;
//                }
//
//                reward = (RewardItem) var1.next();
//            } while (reward.cards == null);
//
//            Iterator var3 = reward.cards.iterator();
//
//            while (var3.hasNext()) {
//                AbstractCard c = (AbstractCard) var3.next();
//                this.onPreviewObtainCard(c);
//            }
//        }
//    }

    public void onPreviewObtainCard(AbstractCard c) {
//        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (DisposableVariable.isDisposableCard(c)) {
            DisposableVariable.setValue(c, DisposableVariable.getValue(c) + 1);
            flash();
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}
