package ShoujoKageki;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.screen.BlackMarketScreen;
import ShoujoKageki.util.IDCheckDontTouchPls;
import ShoujoKageki.variables.DefaultCustomVariable;
import ShoujoKageki.variables.DefaultSecondMagicNumber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ShoujoKageki.variables.DisposableVariable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ShoujoKageki.Res.*;
import static ShoujoKageki.character.KarenCharacter.Enums.CardColor_Karen;
import static ShoujoKageki.character.KarenCharacter.Enums.Karen;

@SpireInitializer
public class ModManager implements ISubscriber, PostDrawSubscriber, EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber,
        EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(ModInfo.ModName);

    private static String modID;
    public static final List<String> allCardIds = new ArrayList<>();

    public ModManager() {
        BaseMod.subscribe(this);
        setModID(ModInfo.ModName);
        Log.logger.info("Done subscribing");


        Log.logger.info("Creating the color " + CardColor_Karen.toString());
        BaseMod.addColor(KarenCharacter.Enums.CardColor_Karen, KarenRenderColor, KarenRenderColor, KarenRenderColor, KarenRenderColor,
                KarenRenderColor, KarenRenderColor, KarenRenderColor, ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY,
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
//        System.out.println("=====================" + abstractCard.name);
    }


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        Log.logger.info("Beginning to edit strings for mod with ID: " + ModInfo.getModId());
        String lang = getLang();
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Card-Strings.json"));

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Power-Strings.json"));

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Relic-Strings.json"));

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Event-Strings.json"));

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Potion-Strings.json"));

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Character-Strings.json"));

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-Orb-Strings.json"));

        BaseMod.loadCustomStringsFile(UIStrings.class,
                ModInfo.getResPath("/localization/" + lang + "/DefaultMod-UI-Strings.json"));

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

        Log.logger.info("Beginning to edit characters. " + "Add " + Karen.toString());
        BaseMod.addCharacter(new KarenCharacter("Karen", Karen), THE_DEFAULT_BUTTON,
                THE_DEFAULT_PORTRAIT, Karen);
        Log.logger.info("Added " + Karen.toString());
    }

    @Override
    public void receiveEditCards() {

        Log.logger.info("Adding variables");
        pathCheck();
        // Add the Custom Dynamic Variables
        Log.logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        BaseMod.addDynamicVariable(new DisposableVariable());

        Log.logger.info("Adding cards");
        String cardsClassPath = this.getClass().getPackage().getName() + ".cards";
        new AutoAdd(ModInfo.getModId()).packageFilter(cardsClassPath).setDefaultSeen(true).any(AbstractCard.class, (info, card) -> {
            BaseMod.addCard(card);
            if (info.seen) {
                UnlockTracker.unlockCard(card.cardID);
            }
            if (card.color == CardColor_Karen) {
                allCardIds.add(card.cardID);
            }
        });
        Log.logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files
                .internal(ModInfo.getResPath("/localization/" + getLang() + "/DefaultMod-Keyword-Strings.json"))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json,
                com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (com.evacipated.cardcrawl.mod.stslib.Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                Log.logger.info("-----------------add keyword: " + keyword.PROPER_NAME);
            }
        }
    }

    @Override
    public void receiveEditRelics() {
        String relicClassPath = this.getClass().getPackage().getName() + ".relics";
        Log.logger.info("===============Adding relics: search in " + relicClassPath);
        for (com.evacipated.cardcrawl.modthespire.ModInfo info : Loader.MODINFOS) {
            Log.logger.info(info.ID);
        }
        new AutoAdd(ModInfo.getModId()).packageFilter(relicClassPath).any(CustomRelic.class, (info, relic) -> {
            BaseMod.addRelicToCustomPool(relic, CardColor_Karen);
//			if (info.seen || relic.tier == RelicTier.STARTER)
            UnlockTracker.markRelicAsSeen(relic.relicId);
            Log.logger.info("Adding relics: " + relic.relicId);
        });
        Log.logger.info("Done adding relics!");
    }

    @Override
    public void receivePostInitialize() {
//        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        BaseMod.addCustomScreen(new BlackMarketScreen());

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }


    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO
    // ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        // String IDjson =
        // Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        // // i hate u Gdx.files
        InputStream in = ModManager.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T
        // EDIT
        // THIS
        // ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP
            // JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        // String IDjson =
        // Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        // // i still hate u btw Gdx.files
        InputStream in = ModManager.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T
        // EDIT
        // THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = ModManager.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE,
        // THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT
                // THIS
            } // NO
        } // NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======
}
