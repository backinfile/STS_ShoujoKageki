package thief.powers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thief.util.TextureLoader;

import static thief.ModInfo.makePowerPath;


public abstract class BasePower extends AbstractPower {
	public AbstractCreature source = null;
	public final String[] DESCRIPTIONS;

	public BasePower(String ID, String actualID, PowerType powerType, String tex84, String tex32,
			final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = actualID;
		PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
		name = powerStrings.NAME;
		description = powerStrings.DESCRIPTIONS[0];
		DESCRIPTIONS = powerStrings.DESCRIPTIONS;

		this.owner = owner;
		this.source = source;
		this.amount = amount;

		type = powerType;
		isTurnBased = false;

		this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath(tex84)), 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath(tex32)), 0, 0, 32, 32);

		updateDescription();
	}

	public BasePower(String ID, PowerType powerType, String tex84, String tex32, final AbstractCreature owner,
			final AbstractCreature source, final int amount) {
		this(ID, ID, powerType, tex84, tex32, owner, source, amount);
	}

	public BasePower(String ID, PowerType powerType, String tex84, String tex32, final int amount) {
		this(ID, powerType, tex84, tex32, AbstractDungeon.player, AbstractDungeon.player, amount);
	}

	public void addToBot(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToBottom(action);
	}

	public void addToTop(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToTop(action);
	}

	@Override
	public void onRemove() {
	}

}
