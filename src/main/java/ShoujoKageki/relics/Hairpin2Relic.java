package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.bag.TowerOfPromise;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Hairpin2Relic extends BaseRelic {
    public static final String RAW_ID = Hairpin2Relic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);

    public Hairpin2Relic() {
        super(ID, RAW_ID, RelicTier.BOSS, LandingSound.FLAT);
    }


    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        flash();
        addToBot(new ApplyBagPowerAction());
        TowerOfPromise card = new TowerOfPromise();
        card.upgrade();
        addToBot(new MakeTempCardInHandAction(card));
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        super.instantObtain(p, slot, callOnEquip);
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        for (int i = 0; i < player.relics.size(); i++) {
            AbstractRelic relic = player.relics.get(i);
            if (relic instanceof HairpinRelic) {
                instantObtain(player, i, true);
                return;
            }
        }
        super.obtain();
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(HairpinRelic.ID);
    }

    @Override
    public void instantObtain() {
        super.instantObtain();
    }
}
