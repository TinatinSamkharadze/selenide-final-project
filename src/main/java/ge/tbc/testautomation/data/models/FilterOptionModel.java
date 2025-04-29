package ge.tbc.testautomation.data.models;

import ge.tbc.testautomation.data.enums.Category;
import ge.tbc.testautomation.data.enums.GuestNumber;
import ge.tbc.testautomation.data.enums.PriceRange;

public class FilterOptionModel {
    private Category category;
    private GuestNumber guestNumber;
    private PriceRange priceRange;

    // Constructor
    public FilterOptionModel(Category category, GuestNumber guestNumber, PriceRange priceRange) {
        this.category = category;
        this.guestNumber = guestNumber;
        this.priceRange = priceRange;
    }

    // Getters and Setters
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public GuestNumber getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(GuestNumber guestNumber) {
        this.guestNumber = guestNumber;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    @Override
    public String toString() {
        return "FilterOptions{" +
                "category=" + category +
                ", guestNumber=" + guestNumber +
                ", priceRange=" + priceRange +
                '}';
    }
}
