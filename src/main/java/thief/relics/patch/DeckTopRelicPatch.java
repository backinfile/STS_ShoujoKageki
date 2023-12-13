package thief.relics.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleLEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleMEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleSEffect;
import thief.Log;
import thief.character.Thief;
import thief.powers.ReduceStrengthLimitPower;
import thief.relics.DeckTopRelic;

import java.util.ArrayList;

public class DeckTopRelicPatch {

    @SpirePatch(
            clz = TheBottomScene.class,
            method = "randomizeTorch"
    )
    public static class RemoveTorchFromScene {
        public static SpireReturn<Void> Prefix(TheBottomScene instance, ArrayList<InteractableTorchEffect> ___torches) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(DeckTopRelic.ID)) {
                Log.logger.info("clear all torch " + ___torches.size());
                ___torches.clear();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
