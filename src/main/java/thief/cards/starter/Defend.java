package thief.cards.starter;

import static thief.ModInfo.makeCardPath;
import static thief.ModInfo.makeID;

import thief.cards.BaseCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends BaseCard {

	public static final String ID = makeID(Defend.class.getSimpleName());
	public static final String IMG = makeCardPath("Skill.png");
	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

	public Defend() {
		super(ID, IMG, COST, TYPE, RARITY, TARGET);
		baseBlock = BLOCK;

		this.tags.add(CardTags.STARTER_DEFEND);

	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}
}
