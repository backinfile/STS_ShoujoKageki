package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FlashAllEnemySpecPowerAction extends AbstractGameAction {
    private final String powerId;

    public FlashAllEnemySpecPowerAction(String powerId) {
        this.powerId = powerId;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractPower power = monster.getPower(powerId);
                if (power != null) {
                    power.flash();
                    power.updateDescription();
                }
            }
            isDone = true;
        }
    }
}
