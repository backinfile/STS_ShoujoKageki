package ShoujoKageki.campfireOption.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import ShoujoKageki.campfireOption.BlackMarketOption;

import java.util.ArrayList;

@SpirePatch(
        clz = CampfireUI.class,
        method = "initializeButtons"
)
public class CampfireOptionPatch {
    public static void Prefix(CampfireUI instance, ArrayList<AbstractCampfireOption> ___buttons) {
        ___buttons.add(new BlackMarketOption());
    }

}
