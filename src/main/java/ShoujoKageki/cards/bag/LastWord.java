package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.RunEffectAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.effects.LastWordScreenEffect;
import ShoujoKageki.effects.LastWordTEffect;
import ShoujoKageki.effects.LastWordVideoEffect;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

public class LastWord extends BaseCard {

    public static final String ID = ModInfo.makeID(LastWord.class.getSimpleName());

    public LastWord() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 999;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // stop attack animation
        p.animX = 0;
        ReflectionHacks.setPrivate(p, AbstractCreature.class, "animationTimer", 0f);

        CardCrawlGame.music.silenceBGMInstantly();
        CardCrawlGame.music.silenceTempBgmInstantly();
        addToBot(new WaitAction(0.5f));
        addToBot(new RunEffectAction(new LastWordVideoEffect(), true));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        FlavorText.AbstractCardFlavorFields.flavor.set(this, "");
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        if (p.hand.size() != 1 || !p.discardPile.isEmpty()) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        if (!BagField.isChangeToDrawPile(false)) {
            if (!p.drawPile.isEmpty()) {
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                return false;
            }
        }
        FlavorText.AbstractCardFlavorFields.flavor.set(this, cardStrings.EXTENDED_DESCRIPTION[1]);
        return true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(9000);
        }
    }
}
