package ShoujoKageki.events;

import ShoujoKageki.ModInfo;
import ShoujoKageki.karen.cards.shine.RealTimeAttack;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;


public class RealTimeEvent extends AbstractImageEvent {
    public static final String RAW_ID = RealTimeEvent.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = ModInfo.makeEventPath(RAW_ID + ".png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public RealTimeEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0], new RealTimeAttack());
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case:
                        // Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave]
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1
                        // and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want
                        // like that)
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new RealTimeAttack(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        break; // Onto screen 1 we go.
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave]
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        break; // Onto screen 1 we go.
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }
}
