package gov.iti.jets.testing.service;

import gov.iti.jets.testing.domain.Order;
import gov.iti.jets.testing.domain.ShoppingCart;
import gov.iti.jets.testing.domain.User;
import gov.iti.jets.testing.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.infrastructure.persistence.Database;
import gov.iti.jets.testing.infrastructure.persistence.OrderDao;
import gov.iti.jets.testing.infrastructure.persistence.UserDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceNew {

    private final SmsGateway smsGateway;

    public Order createOrder(ShoppingCart shoppingCart) {
        Order savedOrder = Database.doInTransaction(em -> {
            User userById = UserDao
                    .findUserById(shoppingCart.getUserId(), em)
                    .orElse(null);

            Order order = Order.of(userById, shoppingCart);
            OrderDao.save(order, em);

            String smsMessage = order.createOrderCreatedSmsMessage();

            // Implicit dependency, shared
            smsGateway.sendSms(userById.getPhoneNumber(), smsMessage);

            return order;
        });
        return savedOrder;
    }
}
