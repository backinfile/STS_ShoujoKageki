package thief.character;

import thief.potions.BasePotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;

public abstract class BasePlayer extends CustomPlayer {
	public static final Logger logger = LogManager.getLogger(BasePlayer.class.getName());


	public BasePlayer(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface,
			AbstractAnimation animation) {
		super(name, playerClass, energyOrbInterface, animation);
		// TODO Auto-generated constructor stub
	}

	public BasePlayer(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, String model,
			String animation) {
		super(name, playerClass, energyOrbInterface, model, animation);
		// TODO Auto-generated constructor stub
	}

	public BasePlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath,
			AbstractAnimation animation) {
		super(name, playerClass, orbTextures, orbVfxPath, animation);
		// TODO Auto-generated constructor stub
	}

	public BasePlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath,
			float[] layerSpeeds, AbstractAnimation animation) {
		super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, animation);
		// TODO Auto-generated constructor stub
	}

	public BasePlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath,
			float[] layerSpeeds, String model, String animation) {
		super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, model, animation);
		// TODO Auto-generated constructor stub
	}

	public BasePlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, String model,
			String animation) {
		super(name, playerClass, orbTextures, orbVfxPath, model, animation);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void applyStartOfCombatPreDrawLogic() {
		super.applyStartOfCombatPreDrawLogic();
	}

	@Override
	public void applyStartOfTurnRelics() {
		logger.info("on base player turn start");
		super.applyStartOfTurnRelics();

		for (AbstractPotion potion : AbstractDungeon.player.potions) {
			if (potion instanceof BasePotion) {
				((BasePotion) potion).triggerOnTurnStart();
			}
		}
	}

	@Override
	public void applyEndOfTurnTriggers() {
		logger.info("on base player turn end");
		super.applyEndOfTurnTriggers();

		for (AbstractPotion potion : AbstractDungeon.player.potions) {
			if (potion instanceof BasePotion) {
				((BasePotion) potion).triggerOnTurnEnd();
			}
		}
	}

	@Override
	public void applyStartOfCombatLogic() {
		logger.info("on base player combat end");
		super.applyStartOfCombatLogic();

		for (AbstractPotion potion : AbstractDungeon.player.potions) {
			if (potion instanceof BasePotion) {
				((BasePotion) potion).triggerOnCombatStart();
			}
		}
	}

	@Override
	public void onVictory() {
		logger.info("on base player victory");
		super.onVictory();

		for (AbstractPotion potion : AbstractDungeon.player.potions) {
			if (potion instanceof BasePotion) {
				((BasePotion) potion).triggerOnVictory();
			}
		}

	}

	@Override
	public void combatUpdate() {
		super.combatUpdate();
//		for (AbstractCard card : replacedCards.group) {
//			card.update();
//		}
	}

	@Override
	public void render(SpriteBatch sb) {

//		for (AbstractCard card : replacedCards.group) {
//			if (card.drawScale > 0.02F) {
//				card.render(sb);
//			}
//		}

		super.render(sb);

	}

}
