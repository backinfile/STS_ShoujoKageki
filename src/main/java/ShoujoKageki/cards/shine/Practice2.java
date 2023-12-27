package ShoujoKageki.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.AccretionField;
import ShoujoKageki.variables.DisposableVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Practice2 extends BaseCard {

    public static final String ID = ModInfo.makeID(Practice2.class.getSimpleName());

    public Practice2() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new TotalShineDescriptionModifier());
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, DisposableVariable.getTotalValueInHand()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            AccretionField.accretion.set(this, true);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void triggerOnDisposed() {
        super.triggerOnDisposed();
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
    }
}
