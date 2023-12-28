package ShoujoKageki.cards.patches;

import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.powers.VoidPower;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class DrawCardPatch {


//    @SpirePatch(
//            clz = DrawCardAction.class,
//            method = "update"
//    )
    public static class Patch {
        public static SpireReturn<Void> Prefix(DrawCardAction instance, @ByRef boolean[] ___clearDrawHistory, AbstractGameAction ___followUpAction) {
            AbstractPower voidPower = AbstractDungeon.player.getPower(VoidPower.POWER_ID);
            if (voidPower == null) return SpireReturn.Continue();
            voidPower.flash();

            if (___clearDrawHistory[0]) {
                ___clearDrawHistory[0] = false;
                DrawCardAction.drawnCards.clear();
            }

            if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
                AbstractDungeon.player.getPower(NoDrawPower.POWER_ID).flash();
            } else if (instance.amount <= 0) {
                // ignore
            } else {
                CardGroup bag = BagField.bag.get(AbstractDungeon.player);
                int deckSize = bag.size();
                if (deckSize == 0) {
                    // ignore
                } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.player.createHandIsFullDialog();
                } else {
                    int toDraw = instance.amount;
                    if (instance.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                        toDraw = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
                        AbstractDungeon.player.createHandIsFullDialog();
                    }
                    AbstractDungeon.actionManager.addToTop(new TakeCardFromBagAction(toDraw));
                }
            }
            endActionWithFollowUp(instance, ___followUpAction);
            return SpireReturn.Return();
        }

        private static void endActionWithFollowUp(DrawCardAction instance, AbstractGameAction ___followUpAction) {
            instance.isDone = true;
            if (___followUpAction != null) {
                AbstractDungeon.actionManager.addToTop(___followUpAction);
            }
        }

    }
}
