package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Spin extends BaseCard {

    private static final boolean LOG = true;

    public static final String ID = ModInfo.makeID(Spin.class.getSimpleName());

    public Spin() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 4;
        this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                reset();
                isDone = true;
            }
        });
    }

    public void reset() {
        magicNumber = baseMagicNumber = 0;
        isMagicNumberModified = false;
        initializeDescription();
    }

    private void onTrigger() {
        this.baseMagicNumber++;
        this.magicNumber = this.baseMagicNumber;
        isMagicNumberModified = true;
        initializeDescription();
        flash();
    }

    @Override
    public void triggerWhenMoveToDiscardPile() {
        super.triggerWhenMoveToDiscardPile();
        onTrigger();
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        onTrigger();
    }

    @Override
    public void triggerOnTakeFromBag() {
        super.triggerOnTakeFromBag();
        onTrigger();
    }

    @Override
    public void triggerOnPutInBag() {
        super.triggerOnPutInBag();
        onTrigger();
    }

    @Override
    public void triggerOnShuffleInfoDrawPile() {
        super.triggerOnShuffleInfoDrawPile();
        onTrigger();
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        reset();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInBag() {
        super.triggerOnEndOfPlayerTurnInBag();
        reset();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInDrawPile() {
        super.triggerOnEndOfPlayerTurnInDrawPile();
        reset();
    }

    @Override
    public void triggerOnEndOfPlayerTurnInDiscardPile() {
        super.triggerOnEndOfPlayerTurnInDiscardPile();
        reset();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}
