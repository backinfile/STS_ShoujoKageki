package ShoujoKageki.cards.bag;

import ShoujoKageki.Log;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class Parry extends BaseCard {
    public static final String ID = makeID(Parry.class.getSimpleName());

    private static final int BASE_BLOCK = 4;
    private static final int UPGRADED_BLOCK = 4;

    private int extraBlock = 0;

    public Parry() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseBlock = BASE_BLOCK;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
//        resetBlock();
    }

    public void resetBlock() {
//        this.baseBlock = BASE_BLOCK;
        extraBlock = 0;
        applyPowers();
    }


    private int lastHandNumber = 0;

    @Override
    public void applyPowers() {
        super.applyPowers();

        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;

        if (!p.hand.contains(this)) {
            lastHandNumber = 0;
            return;
        }
        if (lastHandNumber != p.hand.size()) {
            lastHandNumber = p.hand.size();
            onTrigger();
        }

    }

    private void onTrigger() {
        this.extraBlock += magicNumber;
        this.flash();
        applyPowers();
    }

    public void triggerOnPlayerDrawOrDiscard() {
//        onTrigger();
    }

    @Override
    protected void applyPowersToBlock() {
        this.baseBlock += extraBlock;
        super.applyPowersToBlock();
        this.baseBlock -= extraBlock;
        this.isBlockModified = this.block != this.baseBlock;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        resetBlock();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInBag() {
        super.triggerOnEndOfPlayerTurnInBag();
        resetBlock();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInDrawPile() {
        super.triggerOnEndOfPlayerTurnInDrawPile();
        resetBlock();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInDiscardPile() {
        super.triggerOnEndOfPlayerTurnInDiscardPile();
        resetBlock();
    }

    @Override
    public void moveToDiscardPile() {
        super.moveToDiscardPile();
        resetBlock();
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
        }
    }
}
