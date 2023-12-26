package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.patches.BagField;
import ShoujoKageki.effects.DeckTopEffect;
import ShoujoKageki.ui.DeckTopViewer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class HairpinRelic extends BaseRelic {
    public static final String RAW_ID = HairpinRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public HairpinRelic() {
        super(ID, RAW_ID, RelicTier.STARTER);
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


        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 2)));
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
