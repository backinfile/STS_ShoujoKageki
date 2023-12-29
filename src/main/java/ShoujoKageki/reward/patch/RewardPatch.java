package ShoujoKageki.reward.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class RewardPatch {

    public static class TypePatch {
        @SpireEnum
        public static RewardItem.RewardType SHINE_CARD;
    }
}
