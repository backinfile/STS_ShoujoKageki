package ShoujoKageki.cards.patches;

import ShoujoKageki.cards.patches.field.BagField;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.util.TextureLoader;
import ShoujoKageki.util.Utils2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;

import static ShoujoKageki.ModInfo.makePowerPath;
import static ShoujoKageki.ModInfo.makeUIPath;

public class WaterMarkPatch {
//    private static final TextureRegion WaterMarkTexture = new TextureRegion(TextureLoader.getTexture(makeUIPath("waterMark.png")));
//
//    @SpirePatch2(
//            clz = AbstractCard.class,
//            method = "renderImage"
//    )
//    public static class Render {
//        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
//            if (__instance.color == KarenCharacter.Enums.CardColor_Karen) {
//                TextureRegion img = WaterMarkTexture;
//                float drawScale = __instance.drawScale;
//                float drawX = __instance.current_x;
//                float drawY = __instance.current_y;
//                float angle = __instance.angle;
//
//                sb.setColor(Color.WHITE);
//                sb.draw(img, drawX - (float) img.getRegionWidth() / 2.0F, drawY - (float) img.getRegionHeight() / 2.0F, 0, 0, (float) img.getRegionWidth(), (float) img.getRegionHeight(), drawScale * Settings.scale, drawScale * Settings.scale, angle);
//            }
//        }
//    }
}
