package ShoujoKagekiCore.util;


public class BoolToggleSettingValue {
    public final String name;
    public final boolean defaultValue;
    public final float x;
    public final float y;
    private boolean value;

    public BoolToggleSettingValue(String name, boolean defaultValue, float x, float y) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.x = x;
        this.y = y;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean bool) {
        this.value = bool;
    }
}
