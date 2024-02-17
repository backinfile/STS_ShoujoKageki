package ShoujoKageki_Karen.variables;

import ShoujoKageki_Karen.cards.patches.field.BagField;
import ShoujoKageki_Karen.modifier.ShineCardDescriptionModifier;
import ShoujoKageki.util.Utils2;
import ShoujoKageki_Karen.variables.patch.DisposableField;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import ShoujoKageki_Karen.KarenPath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DisposableVariable extends DynamicVariable { // Shine

    public DisposableVariable() {
    }


    public String key() {
        return KarenPath.makeID("disposable");
    }

    public boolean isModified(AbstractCard card) {
        return DisposableField.disposableModified.get(card);
//        if (card.upgraded) return true;
//        return !Objects.equals(DisposableField.baseDisposable.get(card), DisposableField.disposable.get(card));
    }

    public int value(AbstractCard card) {
        return DisposableField.disposable.get(card);
    }

    public int baseValue(AbstractCard card) {
        return DisposableField.baseDisposable.get(card);
    }

    public boolean upgraded(AbstractCard card) {
        return card.upgraded;
    }


    public static int getValue(AbstractCard card) {
        return DisposableField.disposable.get(card);
    }

    public static int getBaseValue(AbstractCard card) {
        return DisposableField.baseDisposable.get(card);
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
        DisposableVariable.checkModify(card);
    }


    public static void reset(AbstractCard card) {
        int baseValue = DisposableField.baseDisposable.get(card);
        if (baseValue == 0) return;
        int curValue = DisposableField.disposable.get(card);
        if (curValue == -1) return;
        if (curValue < baseValue) {
            setValue(card, baseValue);
        }
        DisposableVariable.checkModify(card);
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

    public static void checkModify(AbstractCard card) {
        DisposableField.disposableModified.set(card,
                DisposableVariable.getValue(card) != DisposableVariable.getBaseValue(card));
    }
}
