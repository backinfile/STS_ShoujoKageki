package ShoujoKageki_Karen.relics.patch;

import ShoujoKageki_Karen.relics.ShineCardRelic;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import javassist.CtBehavior;

public class ShineCardRelicPatch {

    @SpirePatch2(
            clz = ShopScreen.class,
            method = "init"
    )
    public static class Init {
        public static void Postfix(ShopScreen __instance) {
            AbstractRelic relic = AbstractDungeon.player.getRelic(ShineCardRelic.ID);
            if (relic != null) {
//                relic.flash();
                for (AbstractCard card : __instance.coloredCards) {
                    DisposableVariable.setValue(card, DisposableVariable.getValue(card) + 3);
                    card.price = MathUtils.round((float) card.price * ShineCardRelic.Multiplier);
                }
                for (AbstractCard card : __instance.colorlessCards) {
                    DisposableVariable.setValue(card, DisposableVariable.getValue(card) + 3);
                    card.price = MathUtils.round((float) card.price * ShineCardRelic.Multiplier);
                }
            }

        }
    }

    @SpirePatch2(
            clz = StoreRelic.class,
            method = "purchaseRelic"
    )
    public static class OnPurchase {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(StoreRelic __instance, ShopScreen ___shopScreen) {
            AbstractRelic relic = __instance.relic;
            if (relic instanceof ShineCardRelic) {
                for (AbstractCard card : ___shopScreen.coloredCards) {
                    DisposableVariable.setValue(card, DisposableVariable.getValue(card) + 3);
                    card.price = MathUtils.round((float) card.price * ShineCardRelic.Multiplier);
                }
                for (AbstractCard card : ___shopScreen.colorlessCards) {
                    DisposableVariable.setValue(card, DisposableVariable.getValue(card) + 3);
                    card.price = MathUtils.round((float) card.price * ShineCardRelic.Multiplier);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractRelic.class, "relicId");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = ShopScreen.class,
            method = "setPrice",
            paramtypez = AbstractCard.class
    )
    public static class SetPrice {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = "tmpPrice"
        )
        public static void Insert(ShopScreen __instance, AbstractCard card, @ByRef float[] tmpPrice) {
            AbstractRelic relic = AbstractDungeon.player.getRelic(ShineCardRelic.ID);
            if (relic != null) {
//                relic.flash();
                tmpPrice[0] *= ShineCardRelic.Multiplier;
                DisposableVariable.setValue(card, DisposableVariable.getValue(card) + 3);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "price");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
