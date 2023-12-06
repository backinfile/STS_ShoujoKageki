package thief.cards.bag;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.cards.BaseCard;
import thief.character.BasePlayer;

import static thief.ModInfo.makeID;

public class BagDefend extends BaseCard {
    public static final String ID = makeID(BagDefend.class.getSimpleName());

    public BagDefend() {
        super(ID, 4, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 16;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player instanceof BasePlayer) {
            int bagSize = ((BasePlayer) AbstractDungeon.player).bag.size();
            this.costForTurn = Math.max(0, this.cost - Math.min(bagSize, 4));
        }
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
