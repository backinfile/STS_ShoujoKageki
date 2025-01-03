package ShoujoKageki.events;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.shine.RealTimeAttack;
import ShoujoKageki.cards.starter.StageReason;
import ShoujoKageki.reward.ShineCardReward;
import ShoujoKageki.variables.DisposableVariable;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;


public class ShineBookEvent extends AbstractImageEvent {
    public static final String RAW_ID = ShineBookEvent.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = ModInfo.makeEventPath(RAW_ID + ".png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;


    public ShineBookEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0], new StageReason());
        imageEventText.setDialogOption(OPTIONS[1]); // leave
    }

    @Override
    protected void buttonEffect(int btn) { // This is the event:
        switch (screenNum) {
            case 0: // screen number 0
                switch (btn) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2], new StageReason());
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        screenNum = 1;
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new StageReason(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        break; // Onto screen 1 we go.
                    case 1:
                        openMap();
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (btn) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum = 2;
                        AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float) Settings.HEIGHT / 2.0F, relic);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new StageReason(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum = 2;

                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (!DisposableVariable.isDisposableCard(c)) continue;
                            if (DisposableVariable.getBaseValue(c) > DisposableVariable.getValue(c)) {
                                DisposableVariable.setValue(c, DisposableVariable.getBaseValue(c));
                            }

                            float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                            float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                        }
                        break;
                }
                break;
            case 2: // screenNum = 2;
                switch (btn) {
                    case 0:
                        openMap();
                        break;
                }
                break;
        }
    }
}
