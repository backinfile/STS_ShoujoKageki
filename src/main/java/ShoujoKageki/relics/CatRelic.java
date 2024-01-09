package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.powers.ShineRewardPower;
import ShoujoKageki.reward.ShineCardReward;
import ShoujoKageki.util.Utils2;
import ShoujoKageki.variables.patch.DisposableFieldRecordPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CatRelic extends BaseRelic {
    public static final String RAW_ID = CatRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    private final ArrayList<String> rewardedCards = new ArrayList<>();


    public CatRelic() {
        super(ID, RAW_ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onVictory() {
        super.onVictory();

//        int cnt = DisposableFieldRecordPatch.disposedCardsCurTurn.size();
//        for (int i = 0; i < cnt; i++) {
//            ShineCardReward.addShineCardRewardToRoom();
//            flash();
//        }
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        rewardedCards.clear();
    }

    @Override
    public void triggerOnCardDisposed(AbstractCard card) {
        super.triggerOnCardDisposed(card);

        if (!Utils2.inBattlePhase()) {
            return;
        }

        if (rewardedCards.contains(card.cardID)) {
            return;
        }
        rewardedCards.add(card.cardID);
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            ShineCardReward.addShineCardRewardToRoom();
        } else {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShineRewardPower()));
        }
        flash();
    }
}
