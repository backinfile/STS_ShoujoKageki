package thief.cards.starter;

import static thief.ModInfo.makeCardPath;
import static thief.ModInfo.makeID;

import thief.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThiefStrike extends BaseCard {

	public static final String ID = makeID(ThiefStrike.class.getSimpleName());
	public static final String IMG = makeCardPath("Attack.png");

	public ThiefStrike() {
		super(ID, IMG, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
		baseDamage = 6;

		this.tags.add(CardTags.STRIKE);
		this.tags.add(CardTags.STARTER_STRIKE);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
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
