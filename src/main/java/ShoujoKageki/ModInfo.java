package ShoujoKageki;

public class ModInfo {
    public static final String ModName = "ShoujoKageki";
    public static final String AUTHOR = "不方不方";
    public static final String DESCRIPTION = "新增角色：爱城华恋 Aijo Karen";

    public static String getModId() {
        return ModManager.getModID();
    }

    public static String makeID(String idText) {
        return getModId() + ":" + idText;
    }
    public static String getFullModName() {
        return AUTHOR + ":" + getModId();
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
    public static String makeUIPath(String resourcePath) {
        return getResPath("/images/ui/" + resourcePath);
    }

    public static String makeEventPath(String resourcePath) {
        return getResPath("/images/events/" + resourcePath);
    }
    public static String makeAudioPath(String resourcePath) {
        return getResPath("/audio/" + resourcePath);
    }

}
