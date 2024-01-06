package ShoujoKageki.variables;

import ShoujoKageki.Log;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.modifier.ShineCardDescriptionModifier;
import ShoujoKageki.util.Utils2;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import ShoujoKageki.ModInfo;
import ShoujoKageki.variables.patch.DisposableField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Objects;

public class DisposableVariable extends DynamicVariable { // Shine

    public DisposableVariable() {
    }


    public String key() {
        return ModInfo.makeID("disposable");
    }

    public boolean isModified(AbstractCard card) {
        return !Objects.equals(DisposableField.baseDisposable.get(card), DisposableField.disposable.get(card));
    }

    public int value(AbstractCard card) {
        return DisposableField.disposable.get(card);
    }

    public int baseValue(AbstractCard card) {
        return DisposableField.baseDisposable.get(card);
    }

    public boolean upgraded(AbstractCard card) {
        return false;
    }


    public static int getValue(AbstractCard card) {
        return DisposableField.disposable.get(card);
    }


    public static void setAlwaysShine(AbstractCard card) {
        setBaseValue(card, -1);
    }

    public static void setBaseValue(AbstractCard card, int amount) {
        DisposableField.baseDisposable.set(card, amount);
        DisposableField.disposable.set(card, amount);
        if (!CardModifierManager.hasModifier(card, ShineCardDescriptionModifier.ID)) {
            CardModifierManager.addModifier(card, new ShineCardDescriptionModifier());
            card.initializeDescription();
        }
    }

    public static void setValue(AbstractCard card, int amount) {
        DisposableField.disposable.set(card, amount);
        if (!CardModifierManager.hasModifier(card, ShineCardDescriptionModifier.ID)) {
            CardModifierManager.addModifier(card, new ShineCardDescriptionModifier());
            card.initializeDescription();
        }
    }


    public static void reset(AbstractCard card) {
        int baseValue = DisposableField.baseDisposable.get(card);
        if (baseValue == 0) return;
        int curValue = DisposableField.disposable.get(card);
        if (curValue == -1) return;
        if (curValue < baseValue) {
            setValue(card, baseValue);
        }
//        Log.logger.info("reset card " + card.name + " to " + getValue(card));
    }

    public static int getTotalShineValueInDeck() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return 0;
        int cnt = 0;
        for (AbstractCard card : p.masterDeck.group) {
            int value = DisposableVariable.getValue(card);
            if (value > 0) cnt += value;
        }
        return cnt;
    }

    public static int getTotalShineValueInBattle() {
        if (!Utils2.inBattlePhase()) return 0;

        int cnt = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            int value = getValue(card);
            if (value > 0) cnt += value;
        }
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            int value = getValue(card);
            if (value > 0) cnt += value;
        }
        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            int value = getValue(card);
            if (value > 0) cnt += value;
        }
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
            int value = getValue(card);
            if (value > 0) cnt += value;
        }
        if (BagField.showCardsInBag()) {
            for (AbstractCard card : BagField.getBag().group) {
                int value = getValue(card);
                if (value > 0) cnt += value;
            }
        }
        return cnt;
    }

    public static boolean isDisposableCard(AbstractCard card) {
        return DisposableField.baseDisposable.get(card) != 0 || DisposableField.disposable.get(card) != 0;
    }
}
