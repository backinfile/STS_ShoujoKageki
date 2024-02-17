package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.karen.cards.patches.field.BagField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Attack08 extends BaseCard {

    public static final String ID = ModInfo.makeID(Attack08.class.getSimpleName());

    public Attack08() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 3;
        this.baseMagicNumber = this.magicNumber = 2;
        this.bagCardPreviewNumber = this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
//        AbstractPower power = p.getPower(StrengthPower.POWER_ID);
//        if (power != null && power.amount >= magicNumber) {
//            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
//        }
        addToBot(new TakeCardFromBagAction(magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
        if (BagField.hasCardsInBagToDraw()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeDamage(2);
            upgradeMagicNumber(2);
            this.bagCardPreviewNumber = this.magicNumber;
        }
    }
}
