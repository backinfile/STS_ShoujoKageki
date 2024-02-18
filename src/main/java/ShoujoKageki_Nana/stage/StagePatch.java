package ShoujoKageki_Nana.stage;

import ShoujoKageki.Log;
import ShoujoKageki.util.TextureLoader;
import ShoujoKageki_Karen.character.KarenCharacter;
import ShoujoKageki_Nana.NanaPath;
import ShoujoKageki_Nana.character.NanaCharacter;
import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingDeckSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Set;

public class StagePatch {

    @SpirePatch(
            clz = AbstractCard.class,
            method = "<class>"
    )
    public static class Field {
        public static SpireField<StagePosition> stagePosition = new SpireField<>(() -> null);
    }

    public static StagePosition getStagePosition(AbstractCard card) {
        StagePosition stagePosition = Field.stagePosition.get(card);
        if (stagePosition == null) {
            stagePosition = new StagePosition();
            Field.stagePosition.set(card, stagePosition);
        }
        return stagePosition;
    }


    // 初始化 初始的舞台
    @SpireInitializer
    public static class InitStageCard implements ISubscriber, PostCreateStartingDeckSubscriber {
        public static void initialize() {
            BaseMod.subscribe(new InitStageCard());
        }

        @Override
        public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup cardGroup) {
            if (playerClass != NanaCharacter.Enums.ShoujoKageki_Nana) {
                return;
            }
            Log.logger.info("============ init card to stage");
            int curFixed = 0;
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if (card.type == AbstractCard.CardType.POWER) {
                    getStagePosition(card).setPosition(0, 0);
                } else if (card.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                    switch (curFixed) {
                        case 0:
                            getStagePosition(card).setPosition(0, -1);
                            break;
                        case 1:
                            getStagePosition(card).setPosition(-1, 0);
                            break;
                        case 2:
                            getStagePosition(card).setPosition(1, 0);
                            break;
                        case 3:
                            getStagePosition(card).setPosition(0, 1);
                            break;
                    }
                    curFixed++;
                }
            }
            printStageCard();
        }
    }


    private static final TextureRegion STAGE_ICON = new TextureRegion(TextureLoader.getTexture(NanaPath.makeUIPath("stage.png")));

    // 初始化 初始的舞台
    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderEnergy"
    )
    public static class RenderStagePosition {
        public static void Postfix(AbstractCard __instance, SpriteBatch sb, float ___drawScale, float ___angle, float ___transparency) {
            StagePosition stagePosition = getStagePosition(__instance);
            if (stagePosition.invalid) return;
            String text = stagePosition.toString();


            float offsetX = 0 * Settings.scale * ___drawScale;
            float offsetY = -20 * Settings.scale * ___drawScale;

            float drawX = __instance.current_x;
            float drawY = __instance.current_y;
            TextureRegion img = STAGE_ICON;
            sb.setColor(Color.WHITE);
            sb.draw(img,
                    drawX - (float) img.getRegionWidth() / 2.0F + offsetX,
                    drawY - (float) img.getRegionHeight() / 2.0F + offsetY,
                    0f,
                    0f,
                    64f,
                    64f,
                    ___drawScale * Settings.scale,
                    ___drawScale * Settings.scale,
                    ___angle);


            Color costColor = Color.WHITE.cpy();
            costColor.a = ___transparency;
            FontHelper.cardEnergyFont_L.getData().setScale(___drawScale);
            BitmapFont font = FontHelper.cardEnergyFont_L;
            FontHelper.renderRotatedText(sb, font, text, drawX + offsetX, drawY + offsetY, -132.0F * ___drawScale * Settings.scale, 192.0F * ___drawScale * Settings.scale, ___angle, false, costColor);
        }
    }

    public static void printStageCard() {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            StagePosition stagePosition = getStagePosition(card);
            if (stagePosition.invalid) continue;
            Log.logger.info("card:{} position:{}", card.name, stagePosition);
        }
    }
}
