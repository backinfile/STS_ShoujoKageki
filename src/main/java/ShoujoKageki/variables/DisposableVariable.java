package ShoujoKageki.variables;

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

    public static void setValue(AbstractCard card, int amount) {
        DisposableField.disposable.set(card, amount);
        card.initializeDescription();
    }

    public static int getValue(AbstractCard card) {
        return DisposableField.disposable.get(card);
    }

    public static boolean isDisposableCard(AbstractCard card) {
        return DisposableField.baseDisposable.get(card) != 0;
    }

    public static String addDescription(String rawDescription) {
        return uiStrings.TEXT[0] + rawDescription;
    }
}
