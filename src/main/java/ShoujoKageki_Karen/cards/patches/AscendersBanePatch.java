package ShoujoKageki_Karen.cards.patches;

import ShoujoKageki_Karen.cards.starter.Sleepy;
import ShoujoKageki_Karen.character.KarenCharacter;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

public class AscendersBanePatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "dungeonTransitionSetup"
    )
    public static class Patch {

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getClassName().equals(AscendersBane.class.getName())) {
                        e.replace("{if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player instanceof " + KarenCharacter.class.getName() + "){$_= new " + Sleepy.class.getName() + "();} else {$_=$proceed($$);}}");
                    }
                }
            };
        }
    }


//    @SpirePatch2(
//            clz = AbstractDungeon.class,
//            method = "addCurseCards"
//    )
//    public static class AddCurseCards {
//
//        public static void Postfix() {
//            AbstractDungeon.curseCardPool.removeCard(Sleepy.ID);
//        }
//    }
//
//    @SpirePatch2(
//            clz = AbstractPlayer.class,
//            method = "isCursed"
//    )
//    public static class IsCursed {
//        public static SpireReturn<Boolean> Postfix(AbstractPlayer __instance, boolean __result) {
//            if (__result && (__instance instanceof KarenCharacter)) {
//                boolean cursed = false;
//                for (AbstractCard c : __instance.masterDeck.group) {
//                    if (c.type == AbstractCard.CardType.CURSE &&
//                            !Objects.equals(c.cardID, "Necronomicurse") &&
//                            !Objects.equals(c.cardID, "CurseOfTheBell") &&
//                            !Objects.equals(c.cardID, "AscendersBane") &&
//                            !Objects.equals(c.cardID, Sleepy.ID)) {
//                        cursed = true;
//                        break;
//                    }
//                }
//                return SpireReturn.Return(cursed);
//            }
//            return SpireReturn.Return(__result);
//        }
//    }
}
