package com.backinfile.thief;

public class ModInfo {
    public static final String ModName = "thief";
    public static final String ModNameZh = "盗贼 thief";
    public static final String AUTHOR = "不方不方";
    public static final String DESCRIPTION = "盗贼角色mod";


    public static String ModId = "thief";

    public static String getModId() {
        return ModId;
    }

    public static String makeID(String idText) {
        return getModId() + ":" + idText;
    }
    public static String getFullModName() {
        return AUTHOR + ":" + ModId;
    }

    public static String getResPath(String relativePath) {
        return getModId() + "Resources" + relativePath;
    }
    public static String makeCardPath(String resourcePath) {
        return getResPath("/images/cards/" + resourcePath);
    }

    public static String makeRelicPath(String resourcePath) {
        return getResPath("/images/relics/" + resourcePath);
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getResPath("/images/relics/outline/" + resourcePath);
    }

    public static String makeOrbPath(String resourcePath) {
        return getResPath("/images/orbs/" + resourcePath);
    }

    public static String makePowerPath(String resourcePath) {
        return getResPath("/images/powers/" + resourcePath);
    }

    public static String makeEventPath(String resourcePath) {
        return getResPath("/images/events/" + resourcePath);
    }

}
