package ShoujoKageki.variables.patch;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "useCard"
)
public class DisposableFieldPatch {
    public static void Prefix(AbstractPlayer p, AbstractCard card, AbstractMonster monster, int energyOnUse) {
        if (DisposableField.DisposableFields.disposable.get(card) <= -1) {
            return;
        }

        int curValue = DisposableField.DisposableFields.disposable.get(card) - 1;
        DisposableField.DisposableFields.disposable.set(card, curValue);
        card.misc = curValue;
        card.initializeDescription();

        AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(card);
        if (cardInDeck != null) {
            DisposableField.DisposableFields.disposable.set(cardInDeck, curValue);
            cardInDeck.misc = curValue;
            cardInDeck.initializeDescription();
        }

        if (curValue <= 0) {
            PurgeField.purge.set(card, true);
            if (cardInDeck != null) {
                AbstractDungeon.player.masterDeck.removeCard(cardInDeck);
            }
        }
    }
}
