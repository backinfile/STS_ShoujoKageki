package ShoujoKageki_Karen.actions;

import ShoujoKageki_Karen.actions.bag.PlayBagTopCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class BridgeAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final boolean upgraded;
    private final AbstractPlayer p;
    private final int energyOnUse;

    private final int magicNumber;

    public BridgeAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, int magicNumber) {
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
            if (AbstractDungeon.player.endTurnQueued) {
                this.isDone = true;
                return;
            }

            for (int i = 0; i < effect; i++) {
                AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                this.addToBot(new PlayBagTopCardAction(monster, false));
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

