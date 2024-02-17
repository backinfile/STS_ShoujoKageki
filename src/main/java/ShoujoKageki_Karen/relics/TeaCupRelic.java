package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TeaCupRelic extends BaseRelic {
    public static final String RAW_ID = TeaCupRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);


    public TeaCupRelic() {
        super(ID, RAW_ID, RelicTier.UNCOMMON, LandingSound.FLAT);
        setCounter(0);
    }

//
//    @Override
//    public void atBattleStartPreDraw() {
//        super.atBattleStartPreDraw();
//        setCounter(0);
//    }
//
//    @Override
//    public void onVictory() {
//        super.onVictory();
//        setCounter(0);
//    }

    @Override
    public void triggerOnTakeFromBagToHand(AbstractCard card) {
        super.triggerOnTakeFromBagToHand(card);
        if (counter == 1) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1)));
            setCounter(0);
        } else {
            setCounter(counter + 1);
        }
    }
}
