package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.day1.shopping.Product1;
import gov.iti.jets.testing.day1.shopping.ShoppingCart1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    @Test
    void Same_product_added_twice_increments_count() {
        // Arrange
        ShoppingCart1 shoppingCart = new ShoppingCart1();

        // Act
        Product1 shampoo = new Product1("Shampoo", 10 );
        shoppingCart.addProduct( shampoo );
        shoppingCart.addProduct( shampoo );

        // Assert
        Integer count = shoppingCart.getCount( shampoo );

        Assertions.assertThat( count )
                .isEqualTo( 2 );
    }
}