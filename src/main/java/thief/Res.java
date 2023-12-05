package thief;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;

public class Res {

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark
    // Red/Brown

    // Card backgrounds - The actual rectangular card.
    public static final String ATTACK_DEFAULT_GRAY = ModInfo.getResPath("/images/512/bg_attack_default_gray.png");
    public static final String SKILL_DEFAULT_GRAY = ModInfo.getResPath("/images/512/bg_skill_default_gray.png");
    public static final String POWER_DEFAULT_GRAY = ModInfo.getResPath("/images/512/bg_power_default_gray.png");

    public static final String ENERGY_ORB_DEFAULT_GRAY = ModInfo.getResPath("/images/512/card_default_gray_orb.png");
    public static final String CARD_ENERGY_ORB = ModInfo.getResPath("/images/512/card_small_orb.png");

    public static final String ATTACK_DEFAULT_GRAY_PORTRAIT = ModInfo.getResPath("/images/1024/bg_attack_default_gray.png");
    public static final String SKILL_DEFAULT_GRAY_PORTRAIT = ModInfo.getResPath("/images/1024/bg_skill_default_gray.png");
    public static final String POWER_DEFAULT_GRAY_PORTRAIT = ModInfo.getResPath("/images/1024/bg_power_default_gray.png");
    public static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = ModInfo.getResPath("/images/1024/card_default_gray_orb.png");

    // Character assets
    public static final String THE_DEFAULT_BUTTON = ModInfo.getResPath("/images/charSelect/DefaultCharacterButton.png");
    public static final String THE_DEFAULT_PORTRAIT = ModInfo.getResPath("/images/charSelect/DefaultCharacterPortraitBG.png");
    public static final String THE_DEFAULT_SHOULDER_1 = ModInfo.getResPath("/images/char/defaultCharacter/shoulder.png");
    public static final String THE_DEFAULT_SHOULDER_2 = ModInfo.getResPath("/images/char/defaultCharacter/shoulder2.png");
    public static final String THE_DEFAULT_CORPSE = ModInfo.getResPath("/images/char/defaultCharacter/corpse.png");

    // Mod Badge - A small icon that appears in the mod settings menu next to your
    // mod.
    public static final String BADGE_IMAGE = ModInfo.getResPath("/images/Badge.png");

    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = ModInfo.getResPath("/images/char/defaultCharacter/skeleton.atlas");
    public static final String THE_DEFAULT_SKELETON_JSON = ModInfo.getResPath("/images/char/defaultCharacter/skeleton.json");

}
