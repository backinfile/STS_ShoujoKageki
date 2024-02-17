package ShoujoKageki_Karen.effects;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.campfireOption.ShineOption;
import ShoujoKageki_Karen.relics.TapeRelic;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class CampfireShineEffect extends AbstractGameEffect {
    private static String RAW_ID = CampfireShineEffect.class.getSimpleName();
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(KarenPath.makeID(RAW_ID));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final float DUR = 1F;
    private static final float DUR_HALF = 0.5F;
    private final ShineOption shineOption;
    private int state = 0; // 0-Init 1-OpenScreen 2-Finish 3-UnFinish
    private Color screenColor;

    public CampfireShineEffect(ShineOption shineOption) {
        this.shineOption = shineOption;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = DUR;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
//                c.upgrade();
//                AbstractDungeon.player.bottledCardUpgradeCheck(c);
//                DisposableVariable.setAlwaysShine(c);
                DisposableVariable.setValue(c, DisposableVariable.getValue(c) + 3);
                AbstractRelic relic = AbstractDungeon.player.getRelic(TapeRelic.ID);
                if (relic != null) relic.onTrigger();
                shineOption.usable = false;
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                state = 2;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
        }

        if (this.duration <= DUR_HALF && state == 0) { // to open grid
            state = 1;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, TEXT[0], false, false, true, false);
            AbstractDungeon.overlayMenu.cancelButton.show(GridCardSelectScreen.TEXT[1]);
        }

        if (!AbstractDungeon.isScreenUp) { // to update blackScreen
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();

            if (state == 1 && shineOption.usable) { // close by cancel, reopen the campfire
                state = 3;
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    RestRoom r = (RestRoom) AbstractDungeon.getCurrRoom();
                    r.campfireUI.reopen();
                }
            }
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            if (!shineOption.usable) { // used over, exit room
                if (CampfireUI.hidden) {
                    AbstractRoom.waitTimer = 0.0F;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
                }
            }
        }

    }


    private void updateBlackScreenColor() {
        if (this.duration > DUR_HALF) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - DUR_HALF) / (DUR - DUR_HALF));
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / DUR);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }

    }

    public void dispose() {
    }

}

