package ge.tbc.testautomation.data.enums;

public enum GuestNumber {
    TWO(2),
    FOUR(4),
    SIX(6),
    EIGHT(8);

    private final int number;

    GuestNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
