package gov.iti.jets.testing.demo.shopping;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void Same_product_added_twice_increments_count() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart();

        // Act
        Product shampoo = new Product( "Shampoo", 10 );
        shoppingCart.addProduct( shampoo );
        shoppingCart.addProduct( shampoo );

        // Assert
        Integer count = shoppingCart.getCount( shampoo );

        Assertions.assertThat( count )
                .isEqualTo( 2 );
    }
}