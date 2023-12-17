package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.domain.Product;
import gov.iti.jets.testing.domain.ShoppingCart;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ShoppingCartTest_ {
    @Test
    void Receives_and_stores_products() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart(1L);

        // Resistance to refactoring
        // Code pollution -> change production code to test it
        // Broke encapsulation -> clients will depend on implementation detail (collection)
        // State verification of implementation detail

        // Act
        Product panadol = new Product("panadol", 10_000);
        shoppingCart.addProduct(panadol);

        // Assert
        Map<Product, Integer> productMap =
                shoppingCart.getProductToQuantity();

        Assertions.assertThat(productMap.containsKey(panadol))
                .isTrue();
    }

    @Test
    void Keeps_track_of_product_count() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart(1L);

        // Act
        Product shampoo = new Product("Shampoo", 10);
        shoppingCart.addProduct(shampoo);
        shoppingCart.addProduct(shampoo);

        // Assert
        Integer count = shoppingCart.getCount(shampoo);

        Assertions.assertThat(count)
                .isEqualTo(2);
    }

    @Test
    void Calculates_total() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart(1L);

        shoppingCart.addProduct(new Product("panadol", 10_000));
        shoppingCart.addProduct(new Product("pepsi", 5_000));

        // Act
        int total = shoppingCart.calculateTotal();

        // Assert
        Assertions.assertThat(total)
                .isEqualTo(15_000);
    }

}