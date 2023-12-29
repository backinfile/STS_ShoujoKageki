package ShoujoKageki.cards.bag;

import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class Parry extends BaseCard {
    public static final String ID = makeID(Parry.class.getSimpleName());

    private static final int BASE_BLOCK = 4;
    private static final int UPGRADED_BLOCK = 4;

    public Parry() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BASE_BLOCK;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        resetBlock();
    }

    public void triggerOnPlayerDrawOrDiscard() {
        this.baseBlock += magicNumber;
        this.flash();
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        resetBlock();
    }

    @Override
    public void moveToDiscardPile() {
        super.moveToDiscardPile();
        resetBlock();
    }

    public void resetBlock() {
        this.baseBlock = BASE_BLOCK + (upgraded ? UPGRADED_BLOCK : 0);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
        }
    }
}
