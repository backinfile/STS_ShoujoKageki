package ShoujoKageki.karen.relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import ShoujoKageki.karen.relics.DrawExtraRelic;

public class DrawExtraRelicPatch {


    @SpirePatch(
            clz = DrawCardAction.class,
            method = "endActionWithFollowUp"
    )
    public static class RemoveTorchFromScene {
        public static void Postfix(DrawCardAction instance) {
            AbstractRelic relic = AbstractDungeon.player.getRelic(DrawExtraRelic.ID);
            if (relic != null) {
                relic.onTrigger();
            }
        }
    }
}
