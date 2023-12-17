package ShoujoKageki.variables.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import ShoujoKageki.cards.tool.ToolCard;

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
                    DisposableField.DisposableFields.disposable.set(__result, __result.misc);
                    __result.initializeDescription();
                }
            }
        }
        return __result;
    }
}
