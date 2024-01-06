package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.util.Utils2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BackToBack extends BaseCard {

    public static final String ID = ModInfo.makeID(BackToBack.class.getSimpleName());

    public BackToBack() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    private int getBagCardsForGame() {
        if (!Utils2.inBattlePhase()) return 0;
        if (BagField.isInfinite(false)) return 10;
        if (BagField.isChangeToDrawPile(false)) {
            return Math.min(AbstractDungeon.player.drawPile.size(), 10);
        }
        CardGroup bag = BagField.getBag();
        if (bag == null) return 0;
        return Math.min(bag.size(), 10);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += getBagCardsForGame() * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += getBagCardsForGame() * magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        if (this.baseDamage != this.damage) this.isDamageModified = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
