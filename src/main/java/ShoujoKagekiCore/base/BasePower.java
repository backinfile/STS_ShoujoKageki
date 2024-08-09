package ShoujoKagekiCore.base;


import ShoujoKagekiCore.CoreModPath;
import ShoujoKagekiCore.TextureLoader;
import ShoujoKagekiCore.effects.LightFlashPowerEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;


public abstract class BasePower extends AbstractPower {
    public AbstractCreature source = null;
    public boolean exhaustShineCardOnPlay = false; // TODO

    public boolean longPulse;
    private long longPulseEndTime;
    protected long pulseGap = 1000L;

    public BasePower(String ID, PowerType powerType,
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

        setTexture(ID);
        updateDescription();
    }

    public void setTexture(String ID) {
        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(getPath84(ID)), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(getPath32(ID)), 0, 0, 32, 32);
    }

    private static String getPath84(String ID) {
        String[] split = ID.split(":");
        String modId = split[0];
        String powerId = split[1];
        return CoreModPath.makePowerPath(powerId + "84.png").replace(CoreModPath.getModId(), modId);
    }

    private static String getPath32(String ID) {
        String[] split = ID.split(":");
        String modId = split[0];
        String powerId = split[1];
        return CoreModPath.makePowerPath(powerId + "32.png").replace(CoreModPath.getModId(), modId);
    }

    @Override
    public void onRemove() {
    }

    public void startLongPulse() {
        if (longPulse) return;
        longPulse = true;
        longPulseEndTime = System.currentTimeMillis() + pulseGap;
    }

    public void stopPulse() {
        longPulse = false;
    }

    @Override
    public void update(int slot) {
        if (longPulse && longPulseEndTime < System.currentTimeMillis()) {
            longPulseEndTime = System.currentTimeMillis() + pulseGap;
            ArrayList<AbstractGameEffect> effect = ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
            effect.add(new LightFlashPowerEffect(this));
        }
        super.update(slot);
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
