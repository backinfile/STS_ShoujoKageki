package ShoujoKageki_Karen;

import ShoujoKageki_Karen.KarenPath;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;

public class Res {

    // =============== INPUT TEXTURE LOCATION =================

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark
    // Red/Brown

    // Card backgrounds - The actual rectangular card.
    public static final String ATTACK_DEFAULT_GRAY = KarenPath.getResPath("/images/512/bg_attack_default_gray.png");
    public static final String SKILL_DEFAULT_GRAY = KarenPath.getResPath("/images/512/bg_skill_default_gray.png");
    public static final String POWER_DEFAULT_GRAY = KarenPath.getResPath("/images/512/bg_power_default_gray.png");

    public static final String ENERGY_ORB_DEFAULT_GRAY = KarenPath.getResPath("/images/512/card_default_gray_orb.png");
    public static final String CARD_ENERGY_ORB = KarenPath.getResPath("/images/512/card_small_orb.png");

    public static final String ATTACK_DEFAULT_GRAY_PORTRAIT = KarenPath.getResPath("/images/1024/bg_attack_default_gray.png");
    public static final String SKILL_DEFAULT_GRAY_PORTRAIT = KarenPath.getResPath("/images/1024/bg_skill_default_gray.png");
    public static final String POWER_DEFAULT_GRAY_PORTRAIT = KarenPath.getResPath("/images/1024/bg_power_default_gray.png");
    public static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = KarenPath.getResPath("/images/1024/card_default_gray_orb.png");

    // Character assets
    public static final String THE_DEFAULT_BUTTON = KarenPath.getResPath("/images/charSelect/DefaultCharacterButton.png");
    public static final String THE_DEFAULT_PORTRAIT = KarenPath.getResPath("/images/charSelect/DefaultCharacterPortraitBG.png");
    public static final String THE_DEFAULT_SHOULDER_1 = KarenPath.getResPath("/images/char/karen/shoulder.png");
    public static final String THE_DEFAULT_SHOULDER_2 = KarenPath.getResPath("/images/char/karen/shoulder.png");
    public static final String THE_DEFAULT_CORPSE = KarenPath.getResPath("/images/char/karen/corpse.png");

    // Mod Badge - A small icon that appears in the mod settings menu next to your
    // mod.
    public static final String BADGE_IMAGE = KarenPath.getResPath("/images/Badge.png");

    // Colors (RGB)
    // Character Color
    public static final Color KarenRenderColor = CardHelper.getColor(251f, 84f, 88.0f); // ff2600
    public static final Color NanaRenderColor = CardHelper.getColor(253f, 209f, 98f); // fdd162
}
