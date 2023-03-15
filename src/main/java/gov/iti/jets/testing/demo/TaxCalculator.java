package gov.iti.jets.testing.demo;

public class TaxCalculator {
    // Regressions: mess up the percentage
    // Refactor: multiply by 14 then 12 percent
    // Manual testing: spin up a disposable main method
    public int calculateRestaurantTax(int cents) {
        return cents + cents * 28 / 100;
    }
}
