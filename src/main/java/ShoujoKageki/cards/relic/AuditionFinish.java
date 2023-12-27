package ShoujoKageki.cards.relic;

import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.reduceStrength.ReduceStrength3;
import ShoujoKageki.powers.AuditionFinishPower;
import ShoujoKageki.powers.ReduceStrengthLimitPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class AuditionFinish extends BaseCard {
    public static final String ID = makeID(AuditionFinish.class.getSimpleName());

    public AuditionFinish() {
        super(ID, 1, AbstractCard.CardType.POWER, CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
        this.cardsToPreview = new Decay();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AuditionFinishPower()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
