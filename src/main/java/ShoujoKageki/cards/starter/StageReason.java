package ShoujoKageki.cards.starter;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.variables.DisposableVariable;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StageReason extends BaseCard {

    public static final String ID = ModInfo.makeID(StageReason.class.getSimpleName());

    public StageReason() {
        super(ID, 1, CardType.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
        this.color = CardColor.CURSE;
        this.exhaust = true;
//        SoulboundField.soulbound.set(this, true);
        DisposableVariable.setBaseValue(this, 3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upgrade() {
    }
}
