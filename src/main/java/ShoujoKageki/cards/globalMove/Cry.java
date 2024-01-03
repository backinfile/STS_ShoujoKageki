package ShoujoKageki.cards.globalMove;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cry extends BaseCard {

    public static final String ID = ModInfo.makeID(Cry.class.getSimpleName());

    public Cry() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseBlock = 3;
        this.baseMagicNumber = this.magicNumber = 3;
        this.logGlobalMove = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    private void onTrigger() {
        applyPowersToBlock();
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        flash();
    }

    @Override
    public void triggerOnGlobalMove() {
        super.triggerOnGlobalMove();
        onTrigger();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
//            upgradeMagicNumber(2);
        }
    }
}
