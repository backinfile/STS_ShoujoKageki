package ShoujoKageki.karen.cards.other;

import ShoujoKageki.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.SteroidPotion;

import static ShoujoKageki.ModInfo.makeID;

public class EatFood3 extends BaseCard {
    public static final String ID = makeID(EatFood3.class.getSimpleName());

    public EatFood3() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ObtainPotionAction(new SteroidPotion()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
