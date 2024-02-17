package ShoujoKageki_Karen.cards.patches;

import ShoujoKageki_Karen.actions.bag.MoveCardToBagAction;
import ShoujoKageki_Karen.cards.patches.field.PutToBagField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class PutToBagPatch {

    public static boolean checkIfPutToBag(AbstractCard targetCard) {
        if (PutToBagField.putToBag.get(targetCard) || PutToBagField.putToBagOnce.get(targetCard)) {
            PutToBagField.putToBagOnce.set(targetCard, false);
            AbstractPlayer player = AbstractDungeon.player;
            player.onCardDrawOrDiscard();
            player.limbo.addToTop(targetCard);
            AbstractDungeon.actionManager.addToBottom(new MoveCardToBagAction(targetCard));
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(targetCard));
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
                        m.replace("{ if (!" + PutToBagPatch.class.getName() + ".checkIfPutToBag(targetCard)) { $proceed(targetCard); } }");
                    }

                }
            };
        }
    }

}
