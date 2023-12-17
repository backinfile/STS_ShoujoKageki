package ShoujoKageki.cards.tool;

import com.megacrit.cardcrawl.cards.AbstractCard;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import ShoujoKageki.variables.patch.DisposableField;

public abstract class ToolCard extends BaseCard {
    public ToolCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, cost, type, rarity, target);
        DisposableVariable.setBaseValue(this, disposableCnt);
    }

    public ToolCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int disposableCnt) {
        super(id, cost, type, color, rarity, target);
        DisposableVariable.setBaseValue(this, disposableCnt);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        DisposableField.DisposableFields.disposable.set(this, this.misc);
        initializeDescription();
    }

    @Override
    public AbstractCard makeSameInstanceOf() {
        AbstractCard abstractCard = super.makeSameInstanceOf();
        DisposableField.DisposableFields.disposable.set(abstractCard, this.misc);
        return abstractCard;
    }
}