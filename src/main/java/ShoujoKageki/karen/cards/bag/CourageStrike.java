package ShoujoKageki.karen.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.bag.MakeTempCardInBagAction;
import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CourageStrike extends BaseCard {

    public static final String ID = ModInfo.makeID(CourageStrike.class.getSimpleName());

    public CourageStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 6;
        isMultiDamage = true;
        this.cardsToPreview = new Sideways();
        this.tags.add(CardTags.STRIKE);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MakeTempCardInBagAction(this.cardsToPreview.makeCopy(), magicNumber, true, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
