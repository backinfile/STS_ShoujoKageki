package ShoujoKageki_Karen.cards.relic;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.cards.patches.field.ExpectField;
import ShoujoKageki_Karen.powers.GainRelicPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Arrogant extends BaseCard {

    public static final String ID = KarenPath.makeID(Arrogant.class.getSimpleName());

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
