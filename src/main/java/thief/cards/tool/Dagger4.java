package thief.cards.tool;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static thief.ModInfo.makeCardPath;
import static thief.ModInfo.makeID;

public class Dagger4 extends ToolCard {
    public static final String ID = makeID(Dagger4.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    public Dagger4() {
        super(ID, IMG, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, 2);
        baseDamage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
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
