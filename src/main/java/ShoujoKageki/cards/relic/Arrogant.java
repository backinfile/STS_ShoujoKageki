package ShoujoKageki.cards.relic;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.ExpectField;
import ShoujoKageki.powers.ArrogantPower;
import ShoujoKageki.powers.GainRelicPower;
import ShoujoKageki.powers.PassionPower;
import ShoujoKageki.variables.DisposableVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Arrogant extends BaseCard {

    public static final String ID = ModInfo.makeID(Arrogant.class.getSimpleName());

    public Arrogant() {
        super(ID, 4, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        this.tags.add(CardTags.HEALING);
        ExpectField.expect.set(this, true);
        isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GainRelicPower(1)));
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = Color.FIREBRICK.cpy();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }
}
