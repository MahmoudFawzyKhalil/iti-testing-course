package gov.iti.jets.testing.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxCalculatorTest {
    @Test
    void Restaurant_tax_is_28_percent() {
        var calculator = new TaxCalculator();
        int result = calculator.calculateRestaurantTax(100);
        assertEquals(128, result);
    }
}