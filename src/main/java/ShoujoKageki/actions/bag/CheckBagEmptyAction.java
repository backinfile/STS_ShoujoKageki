package ShoujoKageki.actions.bag;

import ShoujoKageki.Log;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKagekiCore.base.BasePower;
import ShoujoKageki.powers.StageIsWaitingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CheckBagEmptyAction extends AbstractGameAction {
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (BagField.isInfinite(false)) {
            isDone = true;
            return;
        }
        if (BagField.isChangeToDrawPile(false)) {
            if (!p.drawPile.isEmpty()) {
                isDone = true;
                return;
            }
        } else {
            if (!BagField.getBag().isEmpty()) {
                isDone = true;
                return;
            }
        }

        for(AbstractPower power: p.powers) {
            if (power instanceof BasePower) ((BasePower) power).triggerOnBagClear();
        }
        isDone = true;
    }
}
