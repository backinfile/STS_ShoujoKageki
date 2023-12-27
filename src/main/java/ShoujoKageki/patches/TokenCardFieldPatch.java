package ShoujoKageki.patches;


import ShoujoKageki.ModInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class TokenCardFieldPatch {
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
        if (card.isLocked || !card.isSeen) {
            return;
        }
        if (!AbstractDungeon.isPlayerInDungeon() || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        if (!TokenCardField.isToken.get(card)) {
            return;
        }
        FontHelper.cardTitleFont.getData().setScale(card.drawScale * 0.7f);
        Color color = Settings.GREEN_TEXT_COLOR.cpy();
        FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, TEXT, card.current_x, card.current_y, 0.0F, 205.0F * card.drawScale * Settings.scale, card.angle, false, color);
    }
}
