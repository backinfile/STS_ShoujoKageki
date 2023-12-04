package com.backinfile.thief.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeckTopViewer {
	public static final Logger logger = LogManager.getLogger(DeckTopViewer.class.getName());

	private AbstractCard oriCard = null;
	private AbstractCard showCard = null;

	private float startScale = 0.24f;
	private float startX = Settings.WIDTH * 0.13F;
	private float startY = AbstractDungeon.player.hb.cY; // Settings.HEIGHT * 0.06F;

	private float curScale = startScale;
	private float curX = startX;
	private float curY = startY;

	public boolean isRunning = false;
	public boolean canDraw = true;
	private final HitboxListener listener;

	public DeckTopViewer() {
		listener = new DeckTopViewerHitboxListener(this);
	}

	public void open(boolean canDraw) {
		isRunning = true;
		this.canDraw = canDraw;
	}

	public void hide() {
		isRunning = false;
	}

	public void update() {
		if (!checkUsable()) {
			showCard = null;
			return;
		}

		AbstractCard topCard = AbstractDungeon.player.drawPile.getTopCard();
		if (topCard != oriCard) {
			oriCard = topCard;
			showCard = oriCard.makeStatEquivalentCopy();
			showCard.lighten(false);
			showCard.unhover();
			if (canDraw) {
				showCard.beginGlowing();
			}
			showCard.drawScale = showCard.targetDrawScale = curScale;
			showCard.current_x = showCard.target_x = curX;
			showCard.current_y = showCard.target_y = curY;
//			logger.info("--------cur_x: " + curX + "cur_y: " + curY);
		}
		if (showCard != null) {
			updateShowCard();
		}
	}

	private boolean checkUsable() {
		if (!isRunning) {
			return false;
		}
		if (AbstractDungeon.player == null) {
			return false;
		}

		if (!AbstractDungeon.isPlayerInDungeon()) {
			return false;
		}

		if (AbstractDungeon.player.isDead) {
			return false;
		}

		if (AbstractDungeon.getCurrRoom() == null) {
			return false;
		}
		if (AbstractDungeon.floorNum <= 0) {
			return false;
		}

		if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) {
			return false;
		}

		if (AbstractDungeon.player.drawPile.isEmpty()) {
			return false;
		}
		return true;
	}

	private void updateShowCard() {
		showCard.applyPowers();
		showCard.hb.encapsulatedUpdate(listener);
		if (showCard.hb.hovered) {
			showCard.targetDrawScale = curScale = 1f;
//			showCard.target_y = curY = AbstractCard.IMG_HEIGHT / 2;
		} else {
			showCard.targetDrawScale = curScale = startScale;
//			showCard.target_y = curY = startY;
		}
		showCard.update();
	}

	public void render(SpriteBatch sb) {
		if (!checkUsable()) {
			showCard = null;
			return;
		}

		if (isRunning && showCard != null) {
			showCard.render(sb);
		}
	}
}
