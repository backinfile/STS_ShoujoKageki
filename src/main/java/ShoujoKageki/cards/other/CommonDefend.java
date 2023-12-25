package ShoujoKageki.cards.other;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ShoujoKageki.actions.DrawMoreByLastDrawAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.relics.DeckTopRelic;

import static ShoujoKageki.ModInfo.makeID;

@AutoAdd.Ignore
public class CommonDefend extends BaseCard {
    public static final String ID = makeID(CommonDefend.class.getSimpleName());

    public CommonDefend() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new DrawCardAction(1, new DrawMoreByLastDrawAction(c -> c.type == CardType.ATTACK, 1)));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic(DeckTopRelic.ID) && !p.drawPile.isEmpty() && p.drawPile.getTopCard().type == CardType.ATTACK) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            initializeDescription();
        }
    }
}
