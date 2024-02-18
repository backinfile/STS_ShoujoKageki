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

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
