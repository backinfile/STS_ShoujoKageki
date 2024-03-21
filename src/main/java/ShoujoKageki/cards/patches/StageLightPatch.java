package ShoujoKageki.cards.patches;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.StageLightImgFocusEffect;
import ShoujoKageki.effects.StageLightImgMultiEffect;
import basemod.BaseMod;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StageLightPatch {
    public static boolean inLight = false;
    public static AbstractCreature inLightTarget = null;
    private static float stayLightTimer = 0f;

    public static float PLAY_CARD_TIME = 10f;//Settings.ACTION_DUR_FAST + StageLightImgMultiEffect.STAY_DURATION;

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "updateInput"
    )
    public static class UpdatePatch {
        public static void Postfix(AbstractPlayer __instance, AbstractMonster ___hoveredMonster) {
            AbstractPlayer player = __instance;
            boolean needToInLight = player.hoveredCard instanceof BaseCard && ((BaseCard) player.hoveredCard).stageLightForTarget > 0 && ___hoveredMonster != null;
            if (needToInLight && (!inLight || ___hoveredMonster != inLightTarget)) {
                if (inLight) closeLight(true);
                inLight = true;
                inLightTarget = ___hoveredMonster;
                AbstractDungeon.effectList.add(new StageLightImgFocusEffect(___hoveredMonster));
            }
            if (!needToInLight && inLight && stayLightTimer <= 0) {
                closeLight(false);
            }

            if (stayLightTimer > 0) {
                stayLightTimer -= Gdx.graphics.getDeltaTime();
                if (stayLightTimer <= 0) {
                    closeLight(false);
                }
            }
        }
    }

    public static void closeLight(boolean instantly) {
        inLight = false;
        stayLightTimer = 0f;
        inLightTarget = null;
        if (instantly) {
            AbstractDungeon.effectList.removeIf(e -> {
                if (e instanceof StageLightImgFocusEffect) {
                    e.dispose();
                    return true;
                }
                return false;
            });
        }
    }

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "playCard"
    )
    public static class PlayCardPatch {
        public static void Postfix(AbstractPlayer __instance) {
            if (inLight) {
                stayLightTimer = PLAY_CARD_TIME;
            }
        }
    }

    @SpireInitializer
    public static class LastCardRegister {
        public static void initialize() {
            BaseMod.subscribe((PostBattleSubscriber) abstractRoom -> closeLight(false));
            BaseMod.subscribe((OnStartBattleSubscriber) abstractRoom -> closeLight(false));
        }
    }
}
