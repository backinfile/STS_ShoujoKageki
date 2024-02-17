package ShoujoKageki_Nana;

public class NanaPath {
    public static final String ModName = "ShoujoKageki";
    public static final String Name = "Nana";

    public static String makeID(String idText) {
        return Name + ":" + idText;
    }

    public static String getResPath(String relativePath) {
        return ModName + Name + "Resources" + relativePath;
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
