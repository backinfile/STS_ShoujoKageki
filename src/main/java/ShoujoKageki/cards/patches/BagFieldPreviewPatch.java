package ShoujoKageki.cards.patches;

import ShoujoKageki.Log;
import ShoujoKageki.SettingsPanel;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.effects.MoveCardToBagEffect;
import ShoujoKageki.util.ActionUtils;
import ShoujoKageki.util.Utils2;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class BagFieldPreviewPatch {

    private static AbstractCard cardInPlay = null;

    public static void startReviewMod(AbstractCard hoveredCard) {
        if (!SettingsPanel.showDrawBagReview) return;

        if (cardInPlay == hoveredCard) return;
        if (!(hoveredCard instanceof BaseCard)) return;
        int bagCardPreviewNumber = ((BaseCard) hoveredCard).bagCardPreviewNumber;
        if (bagCardPreviewNumber <= 0) return;
        if (BagField.isInfinite(false)) return;
        if (BagField.isChangeToDrawPile(false)) return;
        Log.logger.info("start preview");


        CardGroup bag = BagField.getBag();
        CardGroup bagPreview = BagField.getBagPreview();

        int handLeftSize = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size() + 1;
        bagCardPreviewNumber = Math.min(Math.min(handLeftSize, bagCardPreviewNumber), bag.size());
        Log.logger.info("start preview bagCardPreviewNumber = " + bagCardPreviewNumber);

        for (int i = 0; i < bag.size(); i++) {
            AbstractCard previewCard = bag.group.get(i);
            if (i < bagCardPreviewNumber) {
                if (!bagPreview.contains(previewCard)) {
                    bagPreview.group.add(previewCard);
                    float targetScale = 0.3f;
                    previewCard.current_x = AbstractDungeon.player.hb.cX;
                    previewCard.current_y = AbstractDungeon.player.hb.cY;
                    previewCard.drawScale = 0.05f;
                    previewCard.target_x = AbstractDungeon.player.hb.cX + AbstractCard.IMG_WIDTH * Settings.scale * targetScale * (i - bagCardPreviewNumber / 2f + 0.5f);
                    previewCard.target_y = AbstractDungeon.player.hb.cY + AbstractDungeon.player.hb.height / 2f + AbstractCard.IMG_HEIGHT * Settings.scale * targetScale;
                    previewCard.targetDrawScale = targetScale;
                    previewCard.stopGlowing();
                    previewCard.setAngle(0.0F);
                }
            } else {
                if (bagPreview.group.remove(previewCard)) {
                    AbstractDungeon.effectList.add(new MoveCardToBagEffect(previewCard));
                }
            }
        }
    }

    public static void stopPreview() {
        cardInPlay = null;
        CardGroup bag = BagField.getBag();
        if (bag != null) {
            for (AbstractCard card : bag.group) {
                if (BagField.getBagPreview().group.remove(card)) {
                    AbstractDungeon.effectList.add(new MoveCardToBagEffect(card));
                }
            }
        }
    }

    public static void clearPreview() {
        cardInPlay = null;
        CardGroup bagPreview = BagField.getBagPreview();
        if (bagPreview != null) bagPreview.clear();
    }


    @SpirePatch(
            clz = AbstractCard.class,
            method = "update"
    )
    public static class StartPreview {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractCard __instance) {
            startReviewMod(__instance);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class Update {
        public static void Postfix(AbstractPlayer __instance) {
            if (!Utils2.inBattlePhase()) return;

            if (__instance.hoveredCard == null) {
                clearPreview();
            }

            CardGroup bagPreview = BagField.getBagPreview();
            if (bagPreview != null) {
                for (AbstractCard card : bagPreview.group) {
                    card.update();
                }
            }
        }
    }

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class Render {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            if (!Utils2.inBattlePhase()) return;

            CardGroup bagPreview = BagField.getBagPreview();
            if (bagPreview != null) {
                for (AbstractCard card : bagPreview.group) {
                    card.render(sb);
                }
            }
        }
    }
}
