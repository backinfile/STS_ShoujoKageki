package ShoujoKageki_Karen.cards.relic;

import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.powers.GainRelicPower;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static ShoujoKageki_Karen.KarenPath.makeID;

@AutoAdd.Ignore
public class Tragedy extends BaseCard {
    public static final String ID = makeID(Tragedy.class.getSimpleName());

    public Tragedy() {
        super(ID, 0, AbstractCard.CardType.POWER, CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
        this.cardsToPreview = new Decay();
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardsToPreview.makeCopy(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
        addToBot(new ApplyPowerAction(p, p, new GainRelicPower(1)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.cardsToPreview = new Injury();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
//            upgradeBaseCost(0);
        }
    }
}
