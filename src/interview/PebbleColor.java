package interview;

public enum PebbleColor {
    R("  red"), W("white"), B(" blue");

    private final String string;

    PebbleColor(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }
}
