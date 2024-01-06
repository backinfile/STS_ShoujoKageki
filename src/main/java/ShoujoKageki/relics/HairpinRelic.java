package ShoujoKageki.relics;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.bag.TowerOfPromise;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HairpinRelic extends BaseRelic {
    public static final String RAW_ID = HairpinRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    private int turnCount = 0;

    public HairpinRelic() {
        super(ID, RAW_ID, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();

//        AbstractMonster targetMonster = null;
//        int targetHp = 0;
//        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
//            if (monster.currentHealth > targetHp) {
//                targetMonster = monster;
//                targetHp = monster.currentHealth;
//            }
//        }
//        if (targetMonster != null) {
//            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            addToBot(new ApplyPowerAction(targetMonster, AbstractDungeon.player, new VulnerablePower(targetMonster, 1, false)));
//        }


//        AbstractPlayer p = AbstractDungeon.player;
//        addToBot(new RelicAboveCreatureAction(p, this));
//        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2)));
//        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 2)));
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        addToBot(new ApplyBagPowerAction());
        beginLongPulse();
        this.turnCount = 0;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.turnCount++;
        if (this.turnCount == 2) { // 第二回合
            stopPulse();
            flash();
            addToBot(new MakeTempCardInHandAction(new TowerOfPromise()));
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    //    @Override
//    public void onPlayerEndTurn() {
//        super.onPlayerEndTurn();
//
//        int cardsNumInBag = BagField.bag.get(AbstractDungeon.player).size();
//        if (cardsNumInBag > 0) {
//            addToBot(new GainBlockAction(AbstractDungeon.player, cardsNumInBag));
//        }
//    }

}
