package ShoujoKageki.cards.bag;

import ShoujoKageki.powers.NextTurnDrawBagPower;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class WeKnow extends BaseCard {
    public static final String ID = makeID(WeKnow.class.getSimpleName());

    public WeKnow() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (condition(p)) {
            addToBot(new GainEnergyAction(magicNumber));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;
        if (!condition(p)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return true;
    }

    private boolean condition(AbstractPlayer player) {
        if (BagField.isInfinite(false)) return false;
        if (BagField.isChangeToDrawPile(false)) {
            return player.hand.size() == player.drawPile.size();
        }
        CardGroup bag = BagField.getBag();
        if (bag == null) return player.hand.size() == 0;
        return player.hand.size() == bag.size();
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
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
