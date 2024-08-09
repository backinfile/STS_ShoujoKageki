package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.ApplyBagPowerAction;
import ShoujoKageki.actions.DestroyAllCardInDrawPileAction;
import ShoujoKageki.actions.bag.MoveCardToBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.patches.VoidDeckPatch;
import ShoujoKageki.powers.BagPower;
import ShoujoKagekiCore.base.BasePower;
import ShoujoKageki.powers.VoidPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Void extends BaseCard {

    public static final String ID = ModInfo.makeID(Void.class.getSimpleName());

    public Void() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
//        GraveField.grave.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
//        addToBot(new ApplyPowerAction(p, p, new VoidPower()));
        BagField.bagReplace.set(p, true);
        addToBot(new DestroyAllCardInDrawPileAction());
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                VoidDeckPatch.showVoidDeckIcon = true;
                isDone = true;
            }
        });
        addToBot(new MoveCardToBagAction(BagField.getBag().group));
        addToBot(new ApplyBagPowerAction());
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPower power = p.getPower(BagPower.POWER_ID);
                if (power instanceof BasePower) {
                    ((BasePower) power).setTexture(VoidPower.RAW_ID);
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
