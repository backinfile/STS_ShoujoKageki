package com.backinfile.thief.util;

import com.backinfile.thief.ModInfo;
import com.backinfile.thief.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class IDCheck {

    public static void setModID(Class<?> modClass) { // DON'T EDIT
        String ID = ModInfo.getModId();
        String modID;
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        // String IDjson =
        // Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        // // i hate u Gdx.files
        InputStream in = modClass.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T
        // EDIT
        // THIS
        // ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        Log.logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP
            // JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        Log.logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
        ModInfo.ModId = modID;
    } // NO

    private static void pathCheck(String ID, Class<?> modClass) { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        // String IDjson =
        // Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        // // i still hate u btw Gdx.files
        InputStream in = modClass.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T
        // EDIT
        // THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = modClass.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(ID + "Resources"); // PLEASE DON'T EDIT THINGS HERE,
        // THANKS
        if (!ID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(ID)) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + ID); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + ID + "Resources"); // NOT
                // THIS
            } // NO
        } // NO
    }// NO

}
