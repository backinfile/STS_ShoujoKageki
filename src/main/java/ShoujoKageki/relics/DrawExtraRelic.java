package ShoujoKageki.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import ShoujoKageki.ModInfo;

public class DrawExtraRelic extends BaseRelic {
    public static final String RAW_ID = DrawExtraRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public DrawExtraRelic() {
        super(ID, RAW_ID, RelicTier.COMMON);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        setCounter(-1);
    }


    @Override
    public void onCardDraw(AbstractCard drawnCard) { // not use this
        super.onCardDraw(drawnCard);

        if (this.counter == -1) {
            setCounter(1);
            beginLongPulse();
        } else {
            setCounter(this.counter + 1);
        }
        if (this.counter > 5) {
            stopPulse();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if (this.counter <= 5) {
            AbstractPlayer player = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(player, this));
            addToBot(new ApplyPowerAction(player, player, new DrawCardNextTurnPower(player, 1)));
        }
        setCounter(-1);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        setCounter(-1);
    }
}
