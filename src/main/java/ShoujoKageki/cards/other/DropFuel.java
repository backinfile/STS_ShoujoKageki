package ShoujoKageki.cards.other;

import ShoujoKageki.actions.DrawMoreByLastDrawAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.effects.DropFuelEffect;
import ShoujoKageki.relics.DeckTopRelic;
import ShoujoKageki.variables.DisposableVariable;
import ShoujoKageki.variables.patch.DisposableField;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;

import static ShoujoKageki.ModInfo.makeID;

public class DropFuel extends BaseCard {
    public static final String ID = makeID(DropFuel.class.getSimpleName());

    public DropFuel() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        exhaust = true;
        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new DropFuelEffect(), 0.15F));
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new GainEnergyAction(this.magicNumber));
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
