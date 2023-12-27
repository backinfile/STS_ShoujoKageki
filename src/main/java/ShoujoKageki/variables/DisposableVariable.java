package ShoujoKageki.variables;

import ShoujoKageki.Log;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import ShoujoKageki.ModInfo;
import ShoujoKageki.variables.patch.DisposableField;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Objects;

public class DisposableVariable extends DynamicVariable { // Shine

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID("DisposableKeyword"));

    public DisposableVariable() {
    }


    public String key() {
        return ModInfo.makeID("disposable");
    }

    public boolean isModified(AbstractCard card) {
        return !Objects.equals(DisposableField.baseDisposable.get(card), DisposableField.disposable.get(card));
    }

    public int value(AbstractCard card) {
        return (Integer) DisposableField.disposable.get(card);
    }

    public int baseValue(AbstractCard card) {
        return (Integer) DisposableField.baseDisposable.get(card);
    }

    public boolean upgraded(AbstractCard card) {
        return false;
    }

    public static void setBaseValue(AbstractCard card, int amount) {
        DisposableField.baseDisposable.set(card, amount);
        DisposableField.disposable.set(card, amount);
        card.initializeDescription();
    }

    public static void setBaseValueAndDescription(AbstractCard card, int amount) {
        DisposableField.baseDisposable.set(card, amount);
        DisposableField.disposable.set(card, amount);
        card.rawDescription = addDescription(card.rawDescription);
        card.initializeDescription();
    }

    public static void setUpgradeDescription(AbstractCard card, String upgradeDescription) {
        card.rawDescription = addDescription(upgradeDescription);
        card.initializeDescription();
    }


    public static void setValue(AbstractCard card, int amount) {
        DisposableField.disposable.set(card, amount);
        card.initializeDescription();
    }

    public static int getValue(AbstractCard card) {
        return DisposableField.disposable.get(card);
    }

    public static void reset(AbstractCard card) {
        int baseValue = DisposableField.baseDisposable.get(card);
        if (baseValue == 0) return;
        int curValue = DisposableField.disposable.get(card);
        if (curValue < baseValue) {
            setValue(card, baseValue);
        }
        Log.logger.info("reset card " + card.name + " to " + getValue(card));
    }

    public static boolean isDisposableCard(AbstractCard card) {
        return DisposableField.baseDisposable.get(card) != 0;
    }

    private static String addDescription(String rawDescription) {
        return uiStrings.TEXT[0] + rawDescription;
    }
}
