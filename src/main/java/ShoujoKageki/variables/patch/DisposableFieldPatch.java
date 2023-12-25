package ShoujoKageki.variables.patch;

import ShoujoKageki.cards.tool.ToolCard;
import ShoujoKageki.variables.DisposableVariable;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class DisposableFieldPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class UseCard {
        public static void Prefix(AbstractPlayer p, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            int curValue = DisposableField.disposable.get(card);
            if (curValue <= 0) {
                return;
            }
            AbstractCard cardInDeck = StSLib.getMasterDeckEquivalent(card);

            if (curValue == 1) {
                PurgeField.purge.set(card, true);
                if (cardInDeck != null) {
                    AbstractDungeon.player.masterDeck.removeCard(cardInDeck);
                }
            } else {
                curValue--;
                if (cardInDeck != null) {
                    DisposableVariable.setValue(cardInDeck, curValue);
                }
                DisposableVariable.setValue(card, curValue);
            }
        }
    }


    @SpirePatch(
            clz = CardLibrary.class,
            method = "getCopy",
            paramtypez = {String.class, int.class, int.class}
    )
    public static class CopyField {
        public static AbstractCard Postfix(AbstractCard __result) {
            if (__result != null) {
                if (__result.misc != 0) {
                    if (__result instanceof ToolCard) {
                        DisposableField.disposable.set(__result, __result.misc);
                        __result.initializeDescription();
                    }
                }
            }
            return __result;
        }
    }
}
