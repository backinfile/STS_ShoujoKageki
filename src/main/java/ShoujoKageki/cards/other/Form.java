package ShoujoKageki.cards.other;

import ShoujoKageki.actions.RunEffectAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.FormVideoEffect;
import ShoujoKageki.effects.WhirlwindLongEffect;
import ShoujoKageki.patches.AudioPatch;
import ShoujoKageki.powers.FormPower;
import ShoujoKageki.variables.DisposableVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

public class Form extends BaseCard {
    public static final String ID = makeID(Form.class.getSimpleName());

    public Form() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 20;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new RunEffectAction(new FormVideoEffect(), true));
        if (!p.hasPower(FormPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new FormPower()));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
            isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
