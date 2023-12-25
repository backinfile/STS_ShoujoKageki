package ShoujoKageki.cards.bag;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.BagField;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class WeKnow extends BaseCard {
    public static final String ID = makeID(WeKnow.class.getSimpleName());

    public WeKnow() {
        super(ID, 4, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 12;
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
