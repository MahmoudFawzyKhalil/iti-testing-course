package gov.iti.jets.testing.domain;

public class TaxCalculator {
    // Manual testing: spin up a disposable main method

    private final int RESTAURANT_TAX = 278;

    public int calculateRestaurantTax(int cents) {
        return cents + cents * RESTAURANT_TAX / 100;
    }
    
}
