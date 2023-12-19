package ShoujoKageki.campfireOption.patch;

import ShoujoKageki.campfireOption.ShineOption;
import ShoujoKageki.character.BasePlayer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import ShoujoKageki.campfireOption.BlackMarketOption;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = CampfireUI.class,
        method = "initializeButtons"
)
public class CampfireOptionPatch {

    @SpireInsertPatch(
            locator = Locator.class
//            localvars={"sb"}
    )
    public static void Insert(CampfireUI instance, ArrayList<AbstractCampfireOption> ___buttons) {
//        ___buttons.add(new BlackMarketOption());
        if (AbstractDungeon.player instanceof BasePlayer) {
            ___buttons.add(new ShineOption());
        }
    }


    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher("java.util.ArrayList", "iterator");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
