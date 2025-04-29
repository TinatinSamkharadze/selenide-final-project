package ge.tbc.testautomation.data.enums;

public enum PriceRange {
    LOW(0, 50),
    MEDIUM(50, 150),
    HIGH(150, 500);

    private final int minPrice;
    private final int maxPrice;

    PriceRange(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }
}
