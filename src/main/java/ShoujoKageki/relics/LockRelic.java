package ShoujoKageki.relics;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.LockRelicAction;
import ShoujoKageki.patches.OnRelicChangePatch;
import ShoujoKagekiCore.shine.DisposableVariable;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AutoAdd.Ignore
public class LockRelic extends BaseRelic {
    public static final String RAW_ID = LockRelic.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS;
    private final AbstractRelic lockedRelic;
    private final int position;

    public LockRelic(AbstractRelic lockedRelic, int position) {
        super(ID, RelicTier.SPECIAL);
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
