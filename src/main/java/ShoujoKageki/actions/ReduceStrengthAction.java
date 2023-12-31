package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ReduceStrengthAction extends AbstractGameAction {
    private final AbstractCreature source;
    private final AbstractCreature target;
    private final int amount;
    private final boolean recover;

    public ReduceStrengthAction(AbstractCreature source, AbstractCreature target, int amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.startDuration = this.duration = Settings.ACTION_DUR_FAST;
        this.recover = true;
    }

    public ReduceStrengthAction(AbstractCreature source, int amount) {
        this(source, amount, true);
    }

    public ReduceStrengthAction(AbstractCreature source, int amount, boolean recover) {
        this.source = source;
        this.target = null;
        this.amount = amount;
        this.startDuration = this.duration = Settings.ACTION_DUR_FAST;
        this.recover = recover;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (target != null) { // single monster
                this.addToBot(new ApplyPowerAction(target, source, new StrengthPower(target, -amount), -amount));
                if (recover && !target.hasPower(ArtifactPower.POWER_ID)) {
                    this.addToBot(new ApplyPowerAction(target, source, new GainStrengthPower(target, amount), amount));
                }
            } else {
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (monster.isDeadOrEscaped()) continue;
                    this.addToBot(new ApplyPowerAction(monster, source, new StrengthPower(monster, -amount), -amount));
                    if (recover && !monster.hasPower(ArtifactPower.POWER_ID)) {
                        this.addToBot(new ApplyPowerAction(monster, source, new GainStrengthPower(monster, amount), amount));
                    }
                }
            }
            isDone = true;
        }
    }
}
