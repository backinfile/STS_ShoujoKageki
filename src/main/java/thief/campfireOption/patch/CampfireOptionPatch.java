package thief.campfireOption.patch;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import thief.Log;
import thief.campfireOption.BlackMarketOption;
import thief.variables.patch.DisposableField;

import java.lang.reflect.Field;
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
