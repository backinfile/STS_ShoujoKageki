package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.effects.BurnEffect;
import ShoujoKageki.powers.BananaLunchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Burn extends BaseCard {

    public static final String ID = ModInfo.makeID(Burn.class.getSimpleName());

    public Burn() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        this.cardsToPreview = new NextStage();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        BagField.bagInfinite.set(p, true);
        BagField.notifyBagPower();
        addToBot(new MakeTempCardInDrawPileAction(new NextStage(), 1, true, true));
        AbstractDungeon.effectList.add(new BurnEffect());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
