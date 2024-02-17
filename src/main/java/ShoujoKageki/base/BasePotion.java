package ShoujoKageki.base;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class BasePotion extends AbstractPotion {
	public static final Logger logger = LogManager.getLogger(BasePotion.class.getName());

	public BasePotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
		super(name, id, rarity, size, color);
		logger.info("new base potion:" + this.getClass().getName() + " id: " + id);
	}

	public BasePotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect effect,
			Color liquidColor, Color hybridColor, Color spotsColor) {
		super(name, id, rarity, size, effect, liquidColor, hybridColor, spotsColor);
		logger.info("new base potion:" + this.getClass().getName() + " id: " + id);
	}

	public void triggerOnTurnStart() {

	}

	public void triggerOnTurnEnd() {

	}

	public void triggerOnVictory() {

	}

	public void triggerOnCombatStart() {

	}

	public AbstractPotion makeCopy() {
		try {
			return (AbstractPotion) getClass().getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("failed to auto-generate makeCopy for potion: " + ID);
		}
	}

	private String dedupeKeyword(String keyword) {
		String retVal = (String) GameDictionary.parentWord.get(keyword);
		if (retVal != null) {
			return retVal;
		}
		return keyword;
	}

	public void initializeDescription() {
		ArrayList<String> keywords = new ArrayList<>();

		for (String word : description.split(" ")) {
			String tmp = dedupeKeyword(word.trim().toLowerCase());
			if (GameDictionary.keywords.containsKey(tmp)) {
				keywords.add(tmp);
			}
		}

		tips.clear();
		tips.add(new PowerTip(name, description));
		for (String keyword : keywords) {
			tips.add(new PowerTip(TipHelper.capitalize(keyword), GameDictionary.keywords.get(keyword)));
		}
	}

}
