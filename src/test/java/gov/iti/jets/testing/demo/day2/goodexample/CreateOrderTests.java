package gov.iti.jets.testing.demo.day2.goodexample;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.Product;
import gov.iti.jets.testing.day2.shopping.domain.ShoppingCart;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseCleanupExtension;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Or;

class CreateOrderTests {

    @Test
    void Order_with_total_below_one_hundred_pounds_is_invalid() {
        // Arrange
        long irrelevantUserId = 1L;
        ShoppingCart shoppingCart = new ShoppingCart( irrelevantUserId );

        shoppingCart.addProduct( new Product( "xyz", 5_000 ) );

        // Act, Assert
        Assertions.assertThatThrownBy( () -> new Order( shoppingCart ) )
                .hasMessage( "Order value [5000] must be greater than minimum [10000] cents" )
                .isInstanceOf( IllegalArgumentException.class );
    }

    @Test
    void Order_with_more_than_10_of_each_product_is_invalid() {

    }

    @Test
    void Order_created_SMS_message_formatted_correctly() {

    }

}
