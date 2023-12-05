package thief.cards.tool;

import basemod.abstracts.CustomSavable;
import thief.cards.BaseCard;
import thief.variables.DisposableVariable;
import thief.variables.patch.DisposableField;

public abstract class ToolCard extends BaseCard {
    public ToolCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, img, cost, type, rarity, target);
        DisposableVariable.setBaseValue(this, disposableCnt);
    }

    public ToolCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, img, cost, type, color, rarity, target);
        DisposableVariable.setBaseValue(this, disposableCnt);
    }
}