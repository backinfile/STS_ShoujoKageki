package ShoujoKageki.cards;

import ShoujoKageki.character.KarenCharacter;

public abstract class BaseCard extends ShoujoKagekiCore.base.BaseCard {
    public BaseCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    public BaseCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, cost, type, color, rarity, target);
    }

    public BaseCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, cost, type, KarenCharacter.Enums.CardColor_Karen, rarity, target);
    }
}
