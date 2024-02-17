package ShoujoKageki_Karen.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class ConservePowerPatch {
//    @SpirePatch(
//            clz = ConservePower.class,
//            method = "atEndOfRound"
//    )
//    public static class AtEndOfRound {
//        public static SpireReturn<Void> Prefix(ConservePower __instance) {
//            return SpireReturn.Return();
//        }
//    }

    @SpirePatch(
            clz = EnergyManager.class,
            method = "recharge"
    )
    public static class Recharge {

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    super.edit(e);
                    if (e.getClassName().equals(ReducePowerAction.class.getName())) {
                        e.replace("{$_ = new com.megacrit.cardcrawl.actions.utility.WaitAction(0F);}");
                    }
                }
            };
        }
    }
}
