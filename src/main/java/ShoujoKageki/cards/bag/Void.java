package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.ApplyBagPowerAction;
import ShoujoKageki.actions.DestroyAllCardInDrawPileAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.VoidPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Void extends BaseCard {

    public static final String ID = ModInfo.makeID(Void.class.getSimpleName());

    public Void() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        GraveField.grave.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DestroyAllCardInDrawPileAction());
        addToBot(new ApplyPowerAction(p, p, new VoidPower()));
        addToBot(new ApplyBagPowerAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
