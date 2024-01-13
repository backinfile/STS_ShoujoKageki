package ShoujoKageki;

import ShoujoKageki.util.TextureLoader;
import basemod.BaseMod;
import basemod.ModLabeledButton;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

import java.util.Properties;

import static ShoujoKageki.Res.BADGE_IMAGE;
import static ShoujoKageki.character.KarenCharacter.Enums.Karen;

public class SettingsPanel {
    public static boolean showDrawBagReview = false;


    public static final String CONFIG_FILE_NAME = "config";
    public static final Properties settingsProperties = new Properties();

    public static void initProperties() {
        settingsProperties.setProperty("showDrawBagReview", "FALSE");
        try {
            SpireConfig config = new SpireConfig(ModInfo.ModName, CONFIG_FILE_NAME, settingsProperties);
            config.load();
            showDrawBagReview = config.getBool("showDrawBagReview");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties() {
        try {
            SpireConfig config = new SpireConfig(ModInfo.ModName, CONFIG_FILE_NAME, settingsProperties);
            config.setBool("showDrawBagReview", showDrawBagReview);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initPanel() {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModInfo.makeID("settingsPanel"));

        ModPanel settingsPanel = new ModPanel();

        ModLabeledButton unlockAscensionBtn = new ModLabeledButton(
                uiStrings.TEXT[0], 350.0f, 700.0f, Color.BLACK, Settings.RED_TEXT_COLOR,
                FontHelper.charDescFont,
                settingsPanel,
                (button) -> {
                    try {
                        Settings.isTrial = false;
                        AbstractPlayer karen = CardCrawlGame.characterManager.recreateCharacter(Karen);
                        karen.getCharStat().incrementVictory();
                        karen.getCharStat().unlockAscension();
                        for (int i = 0; i < 20; i++) {
                            AbstractDungeon.ascensionLevel = i + 1;
                            karen.getCharStat().incrementAscension();
                        }
                        CardCrawlGame.music.fadeAll();
                        CardCrawlGame.mainMenuScreen = new MainMenuScreen();
                        CardCrawlGame.mainMenuScreen.bg.slideDownInstantly();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        // Create the on/off button:
        ModLabeledToggleButton showDrawBagReviewBtn = new ModLabeledToggleButton(
                uiStrings.TEXT[1], 350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                showDrawBagReview, settingsPanel, (label) -> {
        },
                (button) -> {
                    showDrawBagReview = button.enabled;
                    saveProperties();
                });

        settingsPanel.addUIElement(unlockAscensionBtn);
        settingsPanel.addUIElement(showDrawBagReviewBtn);

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, ModInfo.ModName, ModInfo.AUTHOR, ModInfo.DESCRIPTION, settingsPanel);
    }
}
