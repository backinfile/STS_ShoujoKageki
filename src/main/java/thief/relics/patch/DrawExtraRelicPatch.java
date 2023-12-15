package thief.relics.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;
import thief.relics.DeckTopRelic;
import thief.relics.DrawExtraRelic;

import java.util.ArrayList;

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
