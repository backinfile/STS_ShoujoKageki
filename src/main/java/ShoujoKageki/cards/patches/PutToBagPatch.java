package ShoujoKageki.cards.patches;

import ShoujoKageki.actions.ApplyBagPowerAction;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.cards.patches.field.PutToBagField;
import ShoujoKageki.effects.MoveCardToBagEffect;
import ShoujoKageki.powers.BagDefendPower;
import ShoujoKageki.powers.BagPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
            CardGroup bag = BagField.bag.get(AbstractDungeon.player);
            bag.group.add(targetCard);
            AbstractDungeon.effectList.add(new MoveCardToBagEffect(targetCard));
            AbstractDungeon.actionManager.addToTop(new ApplyBagPowerAction(1));
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
