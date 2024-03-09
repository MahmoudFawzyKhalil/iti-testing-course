package gov.iti.jets.testing.service;

import gov.iti.jets.testing.domain.Order;
import gov.iti.jets.testing.domain.ShoppingCart;
import gov.iti.jets.testing.domain.User;
import gov.iti.jets.testing.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.infrastructure.persistence.Database;
import gov.iti.jets.testing.infrastructure.persistence.OrderDao;
import gov.iti.jets.testing.infrastructure.persistence.UserDao;

public class OrderService {

    private static final OrderService INSTANCE =
            new OrderService(SmsGateway.getInstance());

    private final SmsGateway smsGateway;

    public OrderService(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    public Order createOrder(ShoppingCart shoppingCart) {
        // Business logic - unit test
        Order order = Order.of(shoppingCart);

        Database.doInTransactionWithoutResult(em -> {
            // Data wrangling - integration test
            OrderDao.save(order, em);

            // Data wrangling - integration test
            User user = UserDao
                    .findUserById(order.getUserId(), em)
                    // Fail fast, not necessary to test
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Can't create order for non-existent user!"));

            // Business logic - unit test
            String smsMessage = order.createOrderCreatedSmsMessage();

            // Data wrangling - integration test
            smsGateway.sendSms(user.getPhoneNumber(), smsMessage);
        });

        return order;
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
