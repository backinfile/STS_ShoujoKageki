package ShoujoKageki.cards.ignore;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;

@AutoAdd.Ignore
public class Promise extends BaseCard {

    public static final String ID = ModInfo.makeID(Promise.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Promise() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
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
    public void triggerOnTurnStartInBag() {
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
