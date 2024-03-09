package gov.iti.jets.testing.domain;

import gov.iti.jets.testing.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.infrastructure.persistence.Database;
import gov.iti.jets.testing.infrastructure.persistence.OrderDao;
import gov.iti.jets.testing.infrastructure.persistence.UserDao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "order")
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    public static final int ORDER_MINIMUM = 10_000;

    public static final int MAX_QUANTITY = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "user_id")
    // This @JoinColumn annotation is for documentation only, foreign key should be added using a database migration tool e.g. Flyway
    @JoinColumn(name = "user_id", table = "users", referencedColumnName = "id")
    private Long userId;

    @Getter
    @ElementCollection
    @CollectionTable(name = "order_line_items")
    private Set<LineItem> lineItems;


    public static Order of(User userById, ShoppingCart shoppingCart) {
        if (userById == null) {
            throw new IllegalArgumentException("User is required");
        }
        return of(shoppingCart);
    }

    private Order(ShoppingCart shoppingCart) {
        Set<LineItem> lineItems = shoppingCart.createLineItems();

        validateTotal(shoppingCart.calculateTotal());
        validateQuantities(lineItems);
        this.userId = shoppingCart.getUserId();
        this.lineItems = lineItems;
    }

    private static void validateTotal(int total) {
        if (total < ORDER_MINIMUM) {
            throw new IllegalArgumentException(
                    "Order value [%s] must be greater than minimum [%s] cents"
                            .formatted(total, ORDER_MINIMUM));
        }
    }

    private static void validateQuantities(Set<LineItem> lineItems) {
        lineItems.stream()
                 .map(LineItem::getQuantity)
                 .filter(q -> q > MAX_QUANTITY)
                 .findAny()
                 .ifPresent(q -> {
                     throw new IllegalArgumentException(
                             "Order quantity [%s] is too large, can't purchase more than %s of the same item"
                                     .formatted(q, MAX_QUANTITY));
                 });
    }

    // TODO 010 four quadrants, write test, refactor to service and domain, write unit and integration tests
    public static Order createOrder(ShoppingCart shoppingCart) {
        Order order = of(shoppingCart);

        Database.doInTransactionWithoutResult(em -> {
            OrderDao.save(order, em);


            User user = UserDao
                    .findUserById(order.getUserId(), em)
                    .orElseThrow(() -> new IllegalStateException(
                            "Can't create order for non-existent user!"));

            String smsMessage = order.createOrderCreatedSmsMessage();
            // Implicit dependency, shared
            SmsGateway.getInstance().sendSms(user.getPhoneNumber(), smsMessage);
        });
        return order;
    }

    public static Order of(ShoppingCart shoppingCart) {
        return new Order(shoppingCart);
    }

    public String createOrderCreatedSmsMessage() {
        return lineItems
                .stream()
                .map(i -> "item [%s] x %s".formatted(i.getProductCode(), i.getQuantity()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return "Order{" +
               "userId=" + userId +
               ", lineItems=" + lineItems +
               ", id=" + id +
               '}';
    }
}
