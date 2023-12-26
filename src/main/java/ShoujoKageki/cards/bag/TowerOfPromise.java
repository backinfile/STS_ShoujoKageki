package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.TakeCardFromBagAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.BagField;
import ShoujoKageki.cards.patches.ExpectField;
import ShoujoKageki.cards.patches.PutToBagField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TowerOfPromise extends BaseCard {

    public static final String ID = ModInfo.makeID(TowerOfPromise.class.getSimpleName());

    public TowerOfPromise() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.color = CardColor.COLORLESS;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new TakeCardFromBagAction());
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!BagField.bag.get(p).isEmpty()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//        }
    }
}
