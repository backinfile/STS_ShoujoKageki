package thief.variables.patch;

import com.evacipated.cardcrawl.mod.stslib.actions.common.RefundAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
