package ShoujoKageki.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import ShoujoKageki.ModInfo;
import ShoujoKageki.variables.patch.DisposableField;

import java.util.Objects;

public class DisposableVariable extends DynamicVariable {

    public DisposableVariable() {
    }

    public String key() {
        return ModInfo.makeID("disposable");
    }

    public boolean isModified(AbstractCard card) {
        return !Objects.equals(DisposableField.DisposableFields.baseDisposable.get(card), DisposableField.DisposableFields.disposable.get(card));
    }

    public int value(AbstractCard card) {
        return (Integer) DisposableField.DisposableFields.disposable.get(card);
    }

    public int baseValue(AbstractCard card) {
        return (Integer) DisposableField.DisposableFields.baseDisposable.get(card);
    }

    public boolean upgraded(AbstractCard card) {
        return (Boolean) DisposableField.DisposableFields.isDisposableUpgraded.get(card);
    }

    public static void setBaseValue(AbstractCard card, int amount) {
        card.misc = amount;
        DisposableField.DisposableFields.baseDisposable.set(card, amount);
        DisposableField.DisposableFields.disposable.set(card, amount);
        card.initializeDescription();
    }

    public static void upgrade(AbstractCard card, int amount) {
        DisposableField.DisposableFields.isDisposableUpgraded.set(card, true);
        setBaseValue(card, (Integer) DisposableField.DisposableFields.baseDisposable.get(card) + amount);
    }
}
