package ShoujoKageki_Karen.relics;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseRelic;
import ShoujoKageki_Karen.patches.OnRelicChangePatch;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@AutoAdd.Ignore
public class LockRelic extends BaseRelic {
    public static final String RAW_ID = LockRelic.class.getSimpleName();
    public static final String ID = KarenPath.makeID(RAW_ID);
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS;
    private final AbstractRelic lockedRelic;
    private final int position;

    public LockRelic(AbstractRelic lockedRelic, int position) {
        super(ID, RAW_ID, RelicTier.SPECIAL);
        this.lockedRelic = lockedRelic;
        this.position = position;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    @Override
    public String getUpdatedDescription() {
        if (lockedRelic == null) return "";
        return DESCRIPTIONS[0] + lockedRelic.name + DESCRIPTIONS[1];
    }

    @Override
    public void onVictory() {
        super.onVictory();

        lockedRelic.instantObtain(AbstractDungeon.player, position, false);
        OnRelicChangePatch.publishOnRelicChange();
        lockedRelic.onVictory();
    }

    @Override
    public void triggerOnDead() {
        super.triggerOnDead();
        lockedRelic.instantObtain(AbstractDungeon.player, position, false);
    }
}
