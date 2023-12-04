package com.backinfile.thief;

public class ModInfo {
    public static String ModId = "thief";
    public static final String ModName = "thief";
    public static final String ModNameZh = "盗贼 thief";
    public static final String AUTHOR = "不方不方";
    public static final String DESCRIPTION = "盗贼角色mod";

    public static String getModId() {
        return ModId;
    }
    public static String getFullModName() {
        return ModId + ":" + ModName;
    }
}
