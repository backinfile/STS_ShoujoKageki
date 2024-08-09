package ShoujoKagekiCore;

import ShoujoKagekiCore.util.BoolToggleSettingValue;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ShoujoKagekiCore.CoreRes.BADGE_IMAGE;

public class SettingsPanel {

//    public static BoolToggleSettingValue showDisposedPile = new BoolToggleSettingValue("showDisposedPile", false, 350.0f, 650.0f);

    private static final String CONFIG_FILE_NAME = "config";
    private static final Properties settingsProperties = new Properties();

    private static final List<BoolToggleSettingValue> toggleSettingValueList = new ArrayList<>();

    public static void initProperties() {
        // collect settingValue

        try {
            for (Field field : SettingsPanel.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) && field.getType() == BoolToggleSettingValue.class) {
                    BoolToggleSettingValue value = (BoolToggleSettingValue) field.get(null);
                    if (value != null) {
                        toggleSettingValueList.add(value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Log.logger.error(e);
        }


        for (BoolToggleSettingValue value : toggleSettingValueList) {
            settingsProperties.setProperty(value.name, String.valueOf(value.defaultValue));
        }
        try {
            SpireConfig config = new SpireConfig(CoreModPath.ModName, CONFIG_FILE_NAME, settingsProperties);
            config.load();
            for (BoolToggleSettingValue value : toggleSettingValueList) {
                value.set(config.getBool(value.name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties() {
        try {
            SpireConfig config = new SpireConfig(CoreModPath.ModName, CONFIG_FILE_NAME, settingsProperties);
            for (BoolToggleSettingValue value : toggleSettingValueList) {
                config.setBool(value.name, value.get());
            }
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initPanel() {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(CoreModPath.makeID("settingsPanel"));

        ModPanel settingsPanel = new ModPanel();

        for (BoolToggleSettingValue value : toggleSettingValueList) {
            ModLabeledToggleButton btn = new ModLabeledToggleButton(
                    uiStrings.TEXT_DICT.getOrDefault(value.name, "missing"), value.x, value.y,
                    Settings.CREAM_COLOR, FontHelper.charDescFont,
                    value.get(), settingsPanel, (label) -> {
            },
                    (button) -> {
                        value.set(button.enabled);
                        saveProperties();
                    });
            settingsPanel.addUIElement(btn);
        }
//
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, CoreModPath.ModName, CoreModPath.AUTHOR, CoreModPath.DESCRIPTION, settingsPanel);
    }
}
