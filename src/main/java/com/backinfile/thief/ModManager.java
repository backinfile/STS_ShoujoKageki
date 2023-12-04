package com.backinfile.thief;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.backinfile.thief.character.Thief;
import com.backinfile.thief.util.IDCheck;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;

import static com.backinfile.thief.Res.*;
import static com.backinfile.thief.character.Thief.Enums.COLOR_GRAY;
import static com.backinfile.thief.character.Thief.Enums.THIEF;

@SpireInitializer
public class ModManager implements ISubscriber, PostDrawSubscriber, EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber,
        EditKeywordsSubscriber, EditCharactersSubscriber {

    public ModManager() {
        BaseMod.subscribe(this);
        IDCheck.setModID(this.getClass());
        Log.logger.info("Done subscribing");


        Log.logger.info("Creating the color " + COLOR_GRAY.toString());
        BaseMod.addColor(Thief.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY,
                ENERGY_ORB_DEFAULT_GRAY, ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT,
                POWER_DEFAULT_GRAY_PORTRAIT, ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        Log.logger.info("Done creating the color");
    }

    public static void initialize() {
        Log.logger.info("========================= Initializing Default Mod. Hi. =========================");
        ModManager modManager = new ModManager();
        Log.logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        System.out.println("=====================" + abstractCard.name);
    }


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        Log.logger.info("Beginning to edit strings for mod with ID: " + ModInfo.getModId());
        String lang = getLang();
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Card-Strings.json"));

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Power-Strings.json"));

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Relic-Strings.json"));

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Event-Strings.json"));

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Potion-Strings.json"));

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Character-Strings.json"));

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-Orb-Strings.json"));

        BaseMod.loadCustomStringsFile(UIStrings.class,
                Res.getResPath("/localization/" + lang + "/DefaultMod-UI-Strings.json"));

        Log.logger.info("Done edittting strings");
    }

    private String getLang() {
        Settings.GameLanguage lang = Settings.language;
        if (lang == Settings.GameLanguage.ZHS) {
            return "zhs";
        } else {
            return "eng";
        }
    }

    @Override
    public void receiveEditCharacters() {

        Log.logger.info("Beginning to edit characters. " + "Add " + THIEF.toString());
        BaseMod.addCharacter(new Thief("thief", THIEF), THE_DEFAULT_BUTTON,
                THE_DEFAULT_PORTRAIT, THIEF);
        Log.logger.info("Added " + THIEF.toString());
    }

    @Override
    public void receiveEditCards() {

    }

    @Override
    public void receiveEditKeywords() {

    }

    @Override
    public void receiveEditRelics() {

    }
}
