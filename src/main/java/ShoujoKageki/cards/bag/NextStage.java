package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import basemod.AutoAdd;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NextStage extends BaseCard {

    public static final String ID = ModInfo.makeID(NextStage.class.getSimpleName());

    public NextStage() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    private int curTurn = 0;
    private int triggerCnt = 0;

    private void onTrigger() {
        if (curTurn != GameActionManager.turn) {
            curTurn = GameActionManager.turn;
            triggerCnt = 0;
        }
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) { // 防止无限
            if (triggerCnt++ >= 10) {
                triggerCnt = 0;
                return;
            }
        }

        addToBot(new DrawCardAction(magicNumber));
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

}
