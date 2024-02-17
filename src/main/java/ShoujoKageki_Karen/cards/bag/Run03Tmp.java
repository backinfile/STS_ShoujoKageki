package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.cards.patches.field.BagField;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki_Karen.KarenPath.makeID;

@AutoAdd.Ignore
public class Run03Tmp extends BaseCard {
    public static final String ID = makeID(Run03Tmp.class.getSimpleName());

    public Run03Tmp() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
//        isMultiDamage = true;
//        AccretionField.accretion.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (BagField.hasCardsInBag()) {
                    addToBot(new GainEnergyAction(1));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (BagField.hasCardsInBag()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

//
//    @Override
//    public void triggerOnAccretion() {
//        super.triggerOnAccretion();
//        this.freeToPlayOnce = true;
//    }
}
