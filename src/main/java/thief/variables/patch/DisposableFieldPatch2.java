package thief.variables.patch;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.cards.tool.ToolCard;

@SpirePatch(
        clz = CardLibrary.class,
        method = "getCopy",
        paramtypez = {String.class, int.class, int.class}
)
public class DisposableFieldPatch2 {
    public static AbstractCard Postfix(AbstractCard __result) {
        if (__result != null) {
            if (__result.misc != 0) {
                if (__result instanceof ToolCard) {
                    __result.initializeDescription();
                }
            }
        }
        return __result;
    }
}
