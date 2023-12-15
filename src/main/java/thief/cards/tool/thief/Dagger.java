package thief.cards.tool.thief;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.lwjgl.openal.AL;
import thief.cards.tool.ThiefToolCard;

import java.util.ArrayList;
import java.util.List;

import static thief.ModInfo.makeID;

public class Dagger extends ThiefToolCard {
    public static final String ID = makeID(Dagger.class.getSimpleName());


    public Dagger() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, 1);
        baseDamage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }


    public static final List<AbstractCard> ALL_DAGGER_CARDS;

    static {
        ALL_DAGGER_CARDS = new ArrayList<>();
        ALL_DAGGER_CARDS.add(new Dagger());
        ALL_DAGGER_CARDS.add(new Dagger2());
        ALL_DAGGER_CARDS.add(new Dagger3());
        ALL_DAGGER_CARDS.add(new Dagger4());
        ALL_DAGGER_CARDS.add(new Dagger5());
        ALL_DAGGER_CARDS.add(new Dagger6());
    }

    public static AbstractCard makeRndDagger() {
        int rnd = AbstractDungeon.cardRng.random(ALL_DAGGER_CARDS.size() - 1);
        return ALL_DAGGER_CARDS.get(rnd).makeCopy();
    }
}
