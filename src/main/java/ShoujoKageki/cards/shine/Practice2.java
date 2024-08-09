package ShoujoKageki.cards.shine;

import ShoujoKageki.ModInfo;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.modifier.TotalShineDescriptionModifier;
import ShoujoKagekiCore.shine.DisposableVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Practice2 extends BaseCard {

    public static final String ID = ModInfo.makeID(Practice2.class.getSimpleName());

    public Practice2() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 0;
        CardModifierManager.addModifier(this, new TotalShineDescriptionModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, DisposableVariable.getTotalShineValueInBattle() + magicNumber));
    }

    private int deckSize = 0;
    private boolean lastScreenUp = false;

    @Override
    public void update() {
        super.update();

        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;
        if (AbstractDungeon.isScreenUp != lastScreenUp) {
            lastScreenUp = AbstractDungeon.isScreenUp;
            initializeDescription();
            return;
        }

        if (deckSize != p.masterDeck.size()) {
            deckSize = p.masterDeck.size();
            initializeDescription();
            return;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
