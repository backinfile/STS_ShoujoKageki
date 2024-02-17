package ShoujoKageki_Karen.cards.starter;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sleepy extends BaseCard {

    public static final String ID = KarenPath.makeID(Sleepy.class.getSimpleName());

    public Sleepy() {
        super(ID, -2, CardType.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
        this.color = CardColor.CURSE;
        this.isEthereal = true;
        SoulboundField.soulbound.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upgrade() {
    }
}
