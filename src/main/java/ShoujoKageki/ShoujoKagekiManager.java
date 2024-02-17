package ShoujoKageki;

import ShoujoKageki.reskin.skin.SkinManager;
import ShoujoKageki.util.GenTool;
import ShoujoKageki.util.IDCheckDontTouchPls;
import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpireInitializer
public class ShoujoKagekiManager implements ISubscriber, EditStringsSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(ShoujokagekiPath.ModName);

    private static String modID;

    public ShoujoKagekiManager() {
        BaseMod.subscribe(this);
        setModID(ShoujokagekiPath.ModName);
        SettingsPanel.initProperties();
    }

    public static void initialize() {
        Log.logger.info("========================= Initializing ShoujoKageki Mod. =========================");
        new ShoujoKagekiManager();
        Log.logger.info("========================= /ShoujoKageki Initialized. Hello World./ =========================");
    }


    @Override
    public void receiveEditStrings() {
        Log.logger.info("Beginning to edit strings for mod with ID: " + ShoujokagekiPath.getModId());
        String lang = getLang();

        BaseMod.loadCustomStringsFile(UIStrings.class,
                ShoujokagekiPath.getResPath("/localization/" + lang + "/ShoujoKageki-UI-Strings.json"));
        Log.logger.info("Done edittting strings");
    }

    public static String getLang() {
        Settings.GameLanguage lang = Settings.language;
        if (lang == Settings.GameLanguage.ZHS || lang == Settings.GameLanguage.ZHT) {
            return "zhs";
        } else {
            return "eng";
        }
    }


    @Override
    public void receivePostInitialize() {
        SettingsPanel.initPanel();
        SkinManager.init();
        GenTool.gen();
        logger.info("Done loading badge Image and mod options");
//        logger.info("================== " + CollectDataPatch.getModVersion());
//        for (com.evacipated.cardcrawl.modthespire.ModInfo modInfo : Loader.MODINFOS) {
//            logger.info("================== {} {}", modInfo.ID, modInfo.Name);
//        }
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
        InputStream in = ShoujoKagekiManager.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T
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
        InputStream in = ShoujoKagekiManager.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T
        // EDIT
        // THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = ShoujoKagekiManager.class.getPackage().getName(); // STILL NO EDIT ZONE
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
