package ShoujoKageki.karen.cards.other;

import ShoujoKageki.cards.BaseCard;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class BringStarlight extends BaseCard {
    public static final String ID = makeID(BringStarlight.class.getSimpleName());


    public BringStarlight() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                baseMagicNumber++;
                magicNumber = baseMagicNumber;
                isMagicNumberModified = true;
                initializeDescription();
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            initializeDescription();
        }
    }
}
