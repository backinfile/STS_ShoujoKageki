package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GainDexterityEquityToStrengthAction extends AbstractGameAction {
    @Override
    public void update() {

        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower strengthPower = p.getPower(StrengthPower.POWER_ID);
        int amount = strengthPower != null ? strengthPower.amount : 0;
        if (amount != 0) {
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, amount)));
        }

        isDone = true;
    }
}
