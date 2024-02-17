package ShoujoKageki.actions;

import ShoujoKageki.Res;
import ShoujoKageki.karen.cards.bag.SelectBagPile3;
import ShoujoKageki.karen.cards.bag.SelectDiscardPile3;
import ShoujoKageki.karen.cards.bag.SelectDrawPile3;
import ShoujoKageki.karen.cards.patches.field.BagField;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class AwakePotionAction extends AbstractGameAction {

    private final Data data;

    private static class Data {
        public boolean selectDrawDone = false;
        public boolean selectDiscardDone = false;
        public boolean selectBagDone = false;
    }


    public AwakePotionAction() {
        this(new Data());
    }

    private AwakePotionAction(Data data) {
        this.data = data;
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Res.KarenRenderColor.cpy(), true));
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            isDone = true;
            return;
        }

        checkPileSize(player);

        ArrayList<AbstractCard> choices = new ArrayList<>();
        if (!data.selectDrawDone)
            choices.add(new SelectDrawPile3());
        if (!data.selectBagDone)
            choices.add(new SelectBagPile3());
        if (!data.selectDiscardDone)
            choices.add(new SelectDiscardPile3());

        if (choices.isEmpty()) {
            isDone = true;
            return;
        }
//        if (choices.size() == 1) {
//            choices.get(0).use(player, null);
//            this.addToBot(new AwakePotionAction());
//            isDone = true;
//            return;
//        }
        this.addToBot(new ChooseOneAction(choices));
        isDone = true;
    }

    public static void continueAction() {
        AbstractDungeon.actionManager.addToBottom(new WaitAction(Settings.ACTION_DUR_FAST));
        AbstractDungeon.actionManager.addToBottom(new AwakePotionAction());
    }

    private void checkPileSize(AbstractPlayer player) {
        if (BagField.isChangeToDrawPile(false)) {
            data.selectBagDone = true;
        }
        if (!BagField.hasCardsInBag()) {
            data.selectBagDone = true;
        }
        if (player.drawPile.isEmpty()) {
            data.selectDrawDone = true;
        }
        if (player.discardPile.isEmpty()) {
            data.selectDiscardDone = true;
        }
    }
}
