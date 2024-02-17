package ShoujoKageki.karen.cards.starter;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sleepy extends BaseCard {

    public static final String ID = ModInfo.makeID(Sleepy.class.getSimpleName());

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
