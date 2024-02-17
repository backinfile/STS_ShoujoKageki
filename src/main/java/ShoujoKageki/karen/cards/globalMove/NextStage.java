package ShoujoKageki.karen.cards.globalMove;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NextStage extends BaseCard {

    public static final String ID = ModInfo.makeID(NextStage.class.getSimpleName());

    public NextStage() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
        this.logGlobalMove = true;
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
//            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

//    private int curTurn = 0;
//    private int triggerCnt = 0;

    private void onTrigger() {
//        if (curTurn != GameActionManager.turn) {
//            curTurn = GameActionManager.turn;
//            triggerCnt = 0;
//        }
//        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) { // 防止无限
//            if (triggerCnt++ >= 10) {
//                triggerCnt = 0;
//                return;
//            }
//        }

        addToBot(new DrawCardAction(magicNumber));
        flash();
    }

    @Override
    public void triggerOnTakeFromBag() {
        if (!upgraded) onTrigger();
    }

    @Override
    public void triggerOnPutInBag() {
        super.triggerOnPutInBag();
        if (!upgraded) onTrigger();
    }


    @Override
    public void triggerOnGlobalMove() {
        super.triggerOnGlobalMove();
        if (upgraded) onTrigger();
    }
}
