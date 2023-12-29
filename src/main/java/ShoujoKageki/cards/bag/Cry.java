package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cry extends BaseCard {

    public static final String ID = ModInfo.makeID(Cry.class.getSimpleName());

    public Cry() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    private void onTrigger() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
        flash();
    }

    @Override
    public void onMoveToDiscard() {
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
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
