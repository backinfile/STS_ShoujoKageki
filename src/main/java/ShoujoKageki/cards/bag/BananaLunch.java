package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.DestroyAllCardInDrawPileAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.powers.BananaLunchPower;
import ShoujoKageki.powers.VoidPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BananaLunch extends BaseCard {

    public static final String ID = ModInfo.makeID(BananaLunch.class.getSimpleName());

    public BananaLunch() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardsToPreview = new EatFood2();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p, p, new BananaLunchPower(1)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
