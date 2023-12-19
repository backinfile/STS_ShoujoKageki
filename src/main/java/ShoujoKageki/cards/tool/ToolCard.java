package ShoujoKageki.cards.tool;

import ShoujoKageki.ModInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import ShoujoKageki.variables.patch.DisposableField;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

// 继承此类 自动获得闪耀词条 misc字段占用， 描述更新占用
public abstract class ToolCard extends BaseCard {
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID("DisposableKeyword"));

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
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        DisposableField.DisposableFields.disposable.set(card, this.misc);
        return card;
    }

    @Override
    public void initializeDescription() {
        this.rawDescription = DESCRIPTION + uiStrings.TEXT[0];
        super.initializeDescription();
    }
}