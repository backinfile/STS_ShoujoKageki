package ShoujoKageki.cards.tool;

public abstract class BlackToolCard extends ToolCard{
    public BlackToolCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, cost, type, rarity, target, disposableCnt);
    }

    public BlackToolCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, cost, type, color, rarity, target, disposableCnt);
    }
}
