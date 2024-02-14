package ShoujoKageki.powers;


import ShoujoKageki.Log;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ShoujoKageki.util.TextureLoader;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashPowerEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static ShoujoKageki.ModInfo.makePowerPath;


public abstract class BasePower extends AbstractPower {
    public AbstractCreature source = null;
    public final String[] DESCRIPTIONS;

    public BasePower(String ID, String RAW_ID, PowerType powerType,
                     final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.ID = ID;
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        name = powerStrings.NAME;
        description = powerStrings.DESCRIPTIONS[0];
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = powerType;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath(RAW_ID + "84.png")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath(RAW_ID + "32.png")), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onRemove() {
    }


    public void triggerOnTakeFromBagToHand(AbstractCard card) {

    }

    public void triggerOnTakeFromBag(AbstractCard card) {

    }

    public void triggerOnPutIntoBag(AbstractCard card) {

    }

    public void triggerOnBagClear() {

    }

    public void onCreatureApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

    }

    public void onStrengthIncrease(int after) {

    }

    public void onAttackAfter(DamageInfo info, int damageAmount, AbstractCreature target) {
    }
}
