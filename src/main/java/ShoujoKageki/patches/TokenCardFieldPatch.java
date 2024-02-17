package ShoujoKageki.patches;


import ShoujoKageki.ModInfo;
import ShoujoKageki.karen.cards.patches.field.BagField;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.util.Utils2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

import java.util.ArrayList;

public class TokenCardFieldPatch {
    public static boolean OPEN = true;


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class CreateDrawPile {
        public static void Postfix() {
            ArrayList<CardGroup> cardGroups = new ArrayList<>();
            cardGroups.add(AbstractDungeon.player.hand);
            cardGroups.add(AbstractDungeon.player.drawPile);
            cardGroups.add(AbstractDungeon.player.discardPile);
            cardGroups.add(AbstractDungeon.player.exhaustPile);
            cardGroups.add(AbstractDungeon.player.masterDeck);
            for (CardGroup cardGroup : cardGroups) {
                for (AbstractCard card : cardGroup.group) {
                    if (StSLib.getMasterDeckEquivalent(card) != null) {
                        TokenCardField.isToken.set(card, false);
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeSameInstanceOf"
    )
    public static class MakeSameInstanceOf {
        @SpireInsertPatch(locator = Locator.class, localvars = "card")
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (!TokenCardField.isToken.get(__instance)) {
                TokenCardField.isToken.set(card, false);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "uuid");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


    @SpirePatch(
            clz = AddCardToDeckAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AddCardToDeck {
        public static void Postfix(AddCardToDeckAction __instance, AbstractCard ___cardToObtain) {
            TokenCardField.isToken.set(___cardToObtain, false);
        }
    }

    @SpirePatch(
            clz = Soul.class,
            method = "obtain",
            paramtypez = {AbstractCard.class}
    )
    public static class OnObtain {
        public static void Postfix(Soul __instance, AbstractCard ___card) {
            if (!Utils2.inBattlePhase()) return;
            TokenCardField.isToken.set(___card, false);
            AbstractPlayer p = AbstractDungeon.player;
            if (p != null) {
                for (AbstractCard card : p.hand.group) {
                    if (card.uuid.equals(___card.uuid)) {
                        TokenCardField.isToken.set(card, false);
                    }
                }
                for (AbstractCard card : p.drawPile.group) {
                    if (card.uuid.equals(___card.uuid)) {
                        TokenCardField.isToken.set(card, false);
                    }
                }
                for (AbstractCard card : p.discardPile.group) {
                    if (card.uuid.equals(___card.uuid)) {
                        TokenCardField.isToken.set(card, false);
                    }
                }
                CardGroup bag = BagField.getBag();
                if (bag != null) {
                    for (AbstractCard card : bag.group) {
                        if (card.uuid.equals(___card.uuid)) {
                            TokenCardField.isToken.set(card, false);
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = CardGroup.class,
            method = "addToHand",
            paramtypez = {AbstractCard.class}
    )
    public static class OnAddToHand {
        public static void Postfix(CardGroup __instance, AbstractCard ___c) {
            if (!TokenCardField.isToken.get(___c)) return;
            if (StSLib.getMasterDeckEquivalent(___c) != null) {
                TokenCardField.isToken.set(___c, false);
            }
        }
    }


//    @SpirePatch(
//            clz = MakeTempCardInHandAction.class,
//            method = "update"
//    )
//    public static class MakeTempCardInHand {
//        public static void Prefix(MakeTempCardInHandAction __instance, AbstractCard ___c, boolean ___sameUUID) {
//            if (___sameUUID && StSLib.getMasterDeckEquivalent(___c) != null) {
//                TokenCardField.isToken.set(___c, false);
//            }
//        }
//    }


    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderTitle"
    )
    public static class Render {
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            renderTitle(__instance, sb);
        }
    }

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID(TokenCardField.class.getSimpleName()));
    private static final String TEXT = uiStrings.TEXT[0];

    private static void renderTitle(AbstractCard card, SpriteBatch sb) {
        if (!OPEN) return;
        if (card.isLocked || !card.isSeen) {
            return;
        }
        if (!AbstractDungeon.isPlayerInDungeon() || AbstractDungeon.getCurrMapNode() == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        if (!(AbstractDungeon.player instanceof KarenCharacter)) {
            return;
        }
        if (!TokenCardField.isToken.get(card)) {
            return;
        }
        FontHelper.cardTitleFont.getData().setScale(card.drawScale * 0.7f);
        Color color = Settings.GREEN_TEXT_COLOR.cpy();
        FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, TEXT, card.current_x, card.current_y, 0.0F, 220.0F * card.drawScale * Settings.scale, card.angle, false, color);
    }
}
