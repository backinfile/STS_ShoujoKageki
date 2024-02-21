package ShoujoKageki_Nana.stage;

import ShoujoKageki.Log;
import ShoujoKageki.util.TextureLoader;
import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Nana.NanaPath;
import ShoujoKageki_Nana.character.NanaCharacter;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingDeckSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import javassist.CtBehavior;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class StageFieldPatch {

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
    public static class InitStageCard implements ISubscriber, PostCreateStartingDeckSubscriber, PostInitializeSubscriber {
        public static void initialize() {
            BaseMod.subscribe(new InitStageCard());
        }


        private static final int[] DX = {0, -1, 1, 0};
        private static final int[] DY = {-1, 0, 0, 1};

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
                    if (curFixed < DX.length) {
                        getStagePosition(card).setPosition(DX[curFixed], DY[curFixed]);
                    } else {
                        Log.logger.error("======== found more than 4 strike");
                    }
                    curFixed++;
                }
            }
            printStageCard();
        }

        @Override
        public void receivePostInitialize() {
            BaseMod.addSaveField(NanaPath.makeID("stageField"), new StageFiledSaveClass());
        }


        private static class StageFiledSaveClass implements CustomSavable<ArrayList<String>> {

            @Override
            public ArrayList<String> onSave() {
                ArrayList<String> list = new ArrayList<>();
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    StagePosition stagePosition = getStagePosition(card);
                    if (stagePosition.invalid) {
                        list.add("");
                    } else {
                        list.add(stagePosition.x + "|" + stagePosition.y);
                    }
                }
                return list;
            }

            @Override
            public void onLoad(ArrayList<String> strings) {
                int index = 0;
                for (String save : strings) {
                    if (index >= AbstractDungeon.player.masterDeck.size()) {
                        Log.logger.error("master deck size not match {}", strings);
                        break;
                    }
                    AbstractCard card = AbstractDungeon.player.masterDeck.group.get(index++);
                    if (StringUtils.isEmpty(save)) continue;
                    try {
                        String[] split = save.split("\\|");
                        getStagePosition(card).setPosition(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                    } catch (Exception e) {
                        Log.logger.error("parse stageField error: {}", strings);
                    }
                }
            }
        }
    }

    private static final float offsetX_ori = 0;
    private static final float offsetY_ori = -68;
    private static final float textOffsetX = -132.0F;
    private static final float textOffsetY = 192.0F;

    private static final Texture STAGE_ICON;

    static {
        int width = 512;
        int height = 512;
        Pixmap texture = new Pixmap(Gdx.files.internal(NanaPath.makeUIPath("stage_icon.png")));
        Pixmap icon = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        float scale = 1.2f;
        int offsetX = (int) (width / 2f + textOffsetX + offsetX_ori - texture.getWidth() / 2 * scale);
        int offsetY = (int) (height / 2f - textOffsetY - offsetY_ori - texture.getHeight() / 2 * scale);

        icon.drawPixmap(texture, 0, 0, texture.getWidth(), texture.getHeight(),
                offsetX, offsetY, (int) (texture.getWidth() * scale), (int) (texture.getHeight() * scale)
        );

        Texture stage_icon = new Texture(icon);
        stage_icon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        STAGE_ICON = stage_icon;
    }


    // 初始化 初始的舞台
    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderEnergy"
    )
    public static class RenderStagePosition {
        public static void Postfix(AbstractCard __instance, SpriteBatch sb, float ___drawScale, float ___angle, float ___transparency) {
            StagePosition stagePosition = getStagePosition(__instance);
            if (stagePosition.invalid) return;
            String text = stagePosition.getPositionString();

            float img_scale = 1.2f;
            float text_scale = 0.8f;

            float offsetX = offsetX_ori * Settings.scale * ___drawScale;
            float offsetY = offsetY_ori * Settings.scale * ___drawScale;


            float drawX = __instance.current_x;
            float drawY = __instance.current_y;
            Texture img = STAGE_ICON;
            sb.setColor(Color.WHITE);
            sb.draw(img,
                    drawX - img.getWidth() / 2f,
                    drawY - img.getHeight() / 2f,
                    img.getWidth() / 2f, img.getHeight() / 2f,
                    img.getWidth(), img.getHeight(),
                    ___drawScale * Settings.scale,
                    ___drawScale * Settings.scale,
                    ___angle,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);

            Color costColor = Color.WHITE.cpy();
            costColor.a = ___transparency;
            FontHelper.cardEnergyFont_L.getData().setScale(___drawScale * text_scale);
            BitmapFont font = FontHelper.cardEnergyFont_L;
            FontHelper.renderRotatedText(sb, font, text, drawX, drawY, textOffsetX * ___drawScale * Settings.scale + offsetX, textOffsetY * ___drawScale * Settings.scale + offsetY, ___angle, false, costColor);
        }
    }

    public static void printStageCard() {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            StagePosition stagePosition = getStagePosition(card);
            if (stagePosition.invalid) continue;
            Log.logger.info("card:{} position:{}", card.name, stagePosition);
        }
    }


    @SpirePatch2(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class CopyStageField {
        @SpireInsertPatch(locator = Locator.class, localvars = "card")
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            getStagePosition(card).setPosition(getStagePosition(__instance));
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
