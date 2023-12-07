package thief.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.cards.BaseCard;
import thief.cards.patch.BagField;

import static thief.ModInfo.makeID;

public class BagDefend extends BaseCard {
    public static final String ID = makeID(BagDefend.class.getSimpleName());

    public BagDefend() {
        super(ID, 4, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 16;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int bagSize = BagField.bag.get(AbstractDungeon.player).size();
        this.costForTurn = Math.max(0, this.cost - Math.min(bagSize, 4));
        this.chargeCost = -Math.min(bagSize, 4);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
            initializeDescription();
        }
    }
}
