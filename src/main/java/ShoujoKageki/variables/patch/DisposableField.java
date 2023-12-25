package ShoujoKageki.variables.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "<class>"
)
public class DisposableField {
    public static SpireField<Integer> disposable = new SpireField<>(() -> {
        return -1;
    });
    public static SpireField<Integer> baseDisposable = new SpireField<>(() -> {
        return -1;
    });
}
