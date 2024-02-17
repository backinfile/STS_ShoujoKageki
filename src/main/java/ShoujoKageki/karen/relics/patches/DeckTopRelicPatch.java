package ShoujoKageki.karen.relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;
import ShoujoKageki.karen.relics.DeckTopRelic;

import java.util.ArrayList;

public class DeckTopRelicPatch {

//    @SpirePatch(
//            clz = TheBottomScene.class,
//            method = "randomizeTorch"
//    )
    public static class RemoveTorchFromScene {
        public static SpireReturn<Void> Prefix(TheBottomScene instance, ArrayList<InteractableTorchEffect> ___torches) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(DeckTopRelic.ID)) {
//                Log.logger.info("clear all torch " + ___torches.size());
                ___torches.clear();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
