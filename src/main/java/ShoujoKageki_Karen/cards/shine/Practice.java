package ShoujoKageki_Karen.cards.shine;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Practice extends BaseCard {

    public static final String ID = KarenPath.makeID(Practice.class.getSimpleName());

    public Practice() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 10;
        this.magicNumber = this.baseMagicNumber = 10;
        DisposableVariable.setBaseValue(this, DEFAULT_SHINE_CNT);
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(4);
        }
    }

    @Override
    public void triggerOnDisposed() {
        super.triggerOnDisposed();
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
    }
}
