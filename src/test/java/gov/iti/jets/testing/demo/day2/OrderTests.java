package gov.iti.jets.testing.demo.day2;

import gov.iti.jets.testing.demo.day3.SmsGatewayFake;
import gov.iti.jets.testing.domain.*;
import gov.iti.jets.testing.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.infrastructure.persistence.Database;
import gov.iti.jets.testing.infrastructure.persistence.ProductDao;
import gov.iti.jets.testing.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.service.OrderServiceNew;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;

class OrderTests {

    @Test
    void Create_order_flow() {
        // Arrange
        // Loan pattern / execute around
        User mahmoud = Database.doInTransaction(em -> {
            User user = new User("Mahmoud", "011169540314");
            return UserDao.save(user, em);
        });

        Product panadol = Database.doInTransaction(em -> {
            Product product = new Product("PANADOL", 100_000);
            return ProductDao.save(product, em);
        });

        ShoppingCart shoppingCart = new ShoppingCart(mahmoud.getId());
        shoppingCart.addProduct(panadol);
        shoppingCart.addProduct(panadol);

        SmsGatewayFake smsGatewayFake = new SmsGatewayFake();
        OrderServiceNew orderServiceNew = new OrderServiceNew(smsGatewayFake);

        // Act
        Order order = orderServiceNew.createOrder(shoppingCart);

        // Assert
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(order)
                    .returns(mahmoud.getId(), o -> order.getUserId());
            softly.assertThat(order.getLineItems())
                    .containsExactly(new LineItem("PANADOL", 2));
        }

        smsGatewayFake.verifySmsSent(mahmoud.getPhoneNumber(), "item [PANADOL] x 2");
    }

    // TODO 011 test Order.createOrder()
    @Test
    void Order_with_total_below_one_hundred_pounds_is_invalid() {

    }

    // TODO 012 refactor Order.createOrder()
    @Test
    void Order_with_total_below_one_hundred_pounds_is_invalid_refactor() {
        // Arrange
        long irrelevantUserId = 1L;
        ShoppingCart shoppingCart = new ShoppingCart(irrelevantUserId);

        shoppingCart.addProduct(new Product("xyz", 5_000));

        // Act, Assert
        Assertions.assertThatThrownBy(() -> Order.of(shoppingCart))
                .hasMessage("Order value [5000] must be greater than minimum [10000] cents")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Order_with_more_than_10_of_each_product_is_invalid() {

    }

    @Test
    void Order_created_SMS_message_formatted_correctly() {

    }

}
