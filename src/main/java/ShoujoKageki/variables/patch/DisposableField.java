package ShoujoKageki.variables.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DisposableField {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "<class>"
    )
    public static class DisposableFields {
        public static SpireField<Integer> disposable = new SpireField<>(() -> {
            return -1;
        });
        public static SpireField<Integer> baseDisposable = new SpireField<>(() -> {
            return -1;
        });
        public static SpireField<Boolean> isDisposableUpgraded = new SpireField<>(() -> {
            return false;
        });
    }


}
