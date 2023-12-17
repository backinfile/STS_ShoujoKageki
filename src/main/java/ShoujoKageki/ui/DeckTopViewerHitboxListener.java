package ShoujoKageki.ui;

import ShoujoKageki.relics.DeckTopRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class DeckTopViewerHitboxListener implements HitboxListener {
	private final DeckTopViewer viewer;

	public DeckTopViewerHitboxListener(DeckTopViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void clicked(Hitbox paramHitbox) {
		AbstractPlayer player = AbstractDungeon.player;
		if (viewer.isRunning && viewer.canDraw && player != null && AbstractDungeon.getCurrRoom() != null
				&& AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
			viewer.canDraw = false;
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
			AbstractRelic relic = player.getRelic(DeckTopRelic.ID);
			if (relic != null) {
				relic.stopPulse();
				AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(player, relic));
			}
		}
	}

	@Override
	public void hoverStarted(Hitbox paramHitbox) {
	}

	@Override
	public void startClicking(Hitbox paramHitbox) {
	}

}
