package ShoujoKageki.cards.patches;

import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.actions.bag.MoveCardToBagAction;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.cards.patches.field.PutToBagField;
import ShoujoKageki.effects.MoveCardToBagEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class PutToBagPatch {

    public static boolean checkIfPutToBag(AbstractCard targetCard) {
        if (PutToBagField.putToBag.get(targetCard)) {
            AbstractDungeon.actionManager.addToTop(new MoveCardToBagAction(targetCard));
            return true;
        }
        return false;
    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class MoveToBag {

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(CardGroup.class.getName()) && m.getMethodName().equals("moveToDiscardPile")) {
                        m.replace("{ if (!ShoujoKageki.cards.patches.PutToBagPatch.checkIfPutToBag(targetCard)) { $proceed(targetCard); } }");
                    }

                }
            };
        }
    }

}
