package ShoujoKageki.powers;

import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKagekiCore.base.BasePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static ShoujoKageki.ModInfo.makeID;

public class LetterPower extends BasePower {
    private static final String RAW_ID = LetterPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public LetterPower(int amount) {
        super(POWER_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public void triggerOnPutIntoBag(AbstractCard card) {
        super.triggerOnPutIntoBag(card);
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        flash();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
