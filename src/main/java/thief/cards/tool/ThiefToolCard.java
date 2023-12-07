package thief.cards.tool;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class ThiefToolCard extends ToolCard{
    public ThiefToolCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, cost, type, rarity, target, disposableCnt);
    }

    public ThiefToolCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, cost, type, color, rarity, target, disposableCnt);
    }
}
