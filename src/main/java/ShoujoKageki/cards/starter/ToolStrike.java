package ShoujoKageki.cards.starter;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.cards.tool.ToolCard;

import static ShoujoKageki.ModInfo.makeID;

public class ToolStrike extends ToolCard {

    public static final String ID = makeID(ToolStrike.class.getSimpleName());

    public ToolStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY, 12);
        baseDamage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}
