package thief.cards.tool;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thief.ModInfo;
import thief.actions.MakeTempCardInBagAction;
import thief.cards.BaseCard;
import thief.cards.tool.thief.Dagger;

public class DaggerRnd extends BaseCard {

    public static final String ID = ModInfo.makeID(DaggerRnd.class.getSimpleName());

    public DaggerRnd() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        setCardsToPreviewList(Dagger.ALL_DAGGER_CARDS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MakeTempCardInHandAction(Dagger.makeRndDagger(), 1, true));
        }
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MakeTempCardInBagAction(Dagger.makeRndDagger(), 1, true));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
