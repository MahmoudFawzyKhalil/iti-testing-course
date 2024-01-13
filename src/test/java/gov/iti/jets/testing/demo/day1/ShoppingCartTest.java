package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.domain.Product;
import gov.iti.jets.testing.domain.ShoppingCart;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {
    // TODO 004 resistance to refactoring
    // Add product which depends on implementation details
    // Refactor to use List<LineItem> instead of Map
    @Test
    void Receives_and_stores_products() {
        // Arrange
        long irrelevantUserId = 1L;
        ShoppingCart cart = new ShoppingCart(irrelevantUserId);
        Product abc = new Product("ABC", 1_000);

        // Act
        cart.addProduct(abc);

        // Assert
        Assertions.assertThat(cart.getCount(abc))
                .isOne();
    }

    // TODO 007 state verification
    @Test
    void Keeps_track_of_product_count() {
        // Arrange
        long irrelevantUserId = 1;
        ShoppingCart cart = new ShoppingCart(irrelevantUserId);
        Product abc = new Product("ABC", 1_000);

        // Act
        cart.addProduct(abc);
        cart.addProduct(abc);
        cart.addProduct(abc);

        // Assert
        Assertions.assertThat(cart.getCount(abc))
                .isEqualTo(3);
    }

    // TODO 007 state verification
    @Test
    void Calculates_total() {

    }

    // User name should not have spaces leading, trailing or ' Mahmoud '
    static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public void setName(String name) {
            name = name.trim();
            this.name = name;
        }
    }

    static class UserController {
        void updateUser(long userId, String newName) {
            User user = getUserFromDatabase(userId);
            user.setName(newName);
        }
    }

    static User getUserFromDatabase(long userId) {
        return new User("Mahmoud");
    }

}