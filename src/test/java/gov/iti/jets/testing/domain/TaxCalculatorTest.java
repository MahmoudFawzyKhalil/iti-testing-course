package gov.iti.jets.testing.domain;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {
    void Calculates_restaurant_tax() {
        TaxCalculator taxCalculator = new TaxCalculator();
        taxCalculator.calculateRestaurantTax(100);
    }
}