package ShoujoKageki.patches;

import ShoujoKageki.FTUEUtils;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.powers.BasePower;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

public class TipTrackerPatch {


    @SpirePatch2(
            clz = TipTracker.class,
            method = "refresh"
    )
    public static class DamageAfter {
        public static void Postfix() {
            TipTracker.tips.put(FTUEUtils.COMBAT_TIP_KEY, TipTracker.pref.getBoolean(FTUEUtils.COMBAT_TIP_KEY, false));
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class CreateDrawPile {
        public static void Postfix(AbstractPlayer __instance) {
            if (__instance instanceof KarenCharacter) {
                FTUEUtils.openCombatTip();
            }
        }
    }
}
