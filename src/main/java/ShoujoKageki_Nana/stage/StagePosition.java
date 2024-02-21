package ShoujoKageki_Nana.stage;

public class StagePosition {
    public boolean invalid;
    public int x;
    public int y;

    public StagePosition(int x, int y) {
        this.x = x;
        this.y = y;
        invalid = false;
    }

    public StagePosition() {
        this.invalid = true;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.invalid = false;
    }

    public void setPosition(StagePosition stagePosition) {
        this.invalid = stagePosition.invalid;
        this.x = stagePosition.x;
        this.y = stagePosition.y;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    public String getPositionString() {
        if (invalid) return "NULL";
        String line;
        switch (y) {
            case -1:
                line = "B";
                break;
            case 0:
                line = "P";
                break;
            case 1:
                line = "F";
                break;
            default:
                line = "NULL";
                break;
        }
        return line + x;
    }

}
