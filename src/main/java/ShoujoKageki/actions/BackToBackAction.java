package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class BackToBackAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;

    private int magicNumber;

    public BackToBackAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, int magicNumber) {
        this.p = p;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.magicNumber = magicNumber;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                this.addToBot(new ApplyPowerAction(this.p, this.p, new StrengthPower(this.p, magicNumber)));
                this.addToBot(new ApplyPowerAction(this.p, this.p, new LoseStrengthPower(this.p, magicNumber)));
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

