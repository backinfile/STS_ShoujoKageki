package ShoujoKageki.events;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.starter.StageReason;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.util.Utils2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.text.MessageFormat;


public class PlayMusicEvent extends AbstractImageEvent {
    public static final String RAW_ID = PlayMusicEvent.class.getSimpleName();
    public static final String ID = ModInfo.makeID(RAW_ID);
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = ModInfo.makeEventPath(RAW_ID + ".png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private final int healAmt;
    private final int goldAmt;

    private final int limit = 6;
    private int amt = 0;

    private String musicKey = "";

    public PlayMusicEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        // The first dialogue options available to us.

        healAmt = Math.max((int)(AbstractDungeon.player.currentHealth * 0.2f), 6);
        goldAmt = 90;

        imageEventText.setDialogOption(MessageFormat.format(OPTIONS[0], healAmt), new StageReason());
        imageEventText.setDialogOption(MessageFormat.format(OPTIONS[1], goldAmt), new StageReason());
        imageEventText.setDialogOption(OPTIONS[2], new StageReason());
        imageEventText.setDialogOption(OPTIONS[3]); // leave
    }

    public void playMusic(String key) {
        if (!musicKey.equals("")) {
            CardCrawlGame.sound.stop(musicKey);
        }
        musicKey = key;
        CardCrawlGame.sound.play(key);
    }

    @Override
    protected void buttonEffect(int btn) { // This is the event:
        switch (screenNum) {
            case 0: // screen number 0
                switch (btn) {
                    case 0:
                        playMusic(AudioPatch.Sound_Event_FancyYou);
                        AbstractDungeon.player.heal(healAmt);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new StageReason(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        amt++;
                        break;
                    case 1:
                        playMusic(AudioPatch.Sound_Event_StarDiamond);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmt));
                        AbstractDungeon.player.gainGold(goldAmt);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new StageReason(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        amt++;
                        break;
                    case 2:
                        playMusic(AudioPatch.Karen_Event_Shoujo);
                        if (AbstractDungeon.player.hasRelic("Sozu")) {
                            AbstractDungeon.player.getRelic("Sozu").flash();
                        } else {
                            AbstractPotion p = PotionHelper.getRandomPotion();
                            AbstractDungeon.player.obtainPotion(p);
                        }
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new StageReason(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        amt++;
                        break;
                    case 3:
                        openMap();
                        break;
                }

                if (amt >= limit) {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    imageEventText.clearAllDialogs();
                    imageEventText.setDialogOption(OPTIONS[3]); // leave
                    screenNum = 1;
                } else {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
                    imageEventText.clearAllDialogs();
                    imageEventText.setDialogOption(MessageFormat.format(OPTIONS[0], healAmt), new StageReason());
                    imageEventText.setDialogOption(MessageFormat.format(OPTIONS[1], goldAmt), new StageReason());
                    imageEventText.setDialogOption(OPTIONS[2], new StageReason());
                    imageEventText.setDialogOption(OPTIONS[3]); // leave
                }
                break;
            case 1: // screen number 1
                switch (btn) {
                    case 0:
                        openMap();
                        break;
                }
        }
    }
}
