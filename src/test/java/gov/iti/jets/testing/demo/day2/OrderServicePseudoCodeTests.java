package gov.iti.jets.testing.demo.day2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class OrderServicePseudoCodeTests {
    static class OrderService {
        OrderRepository orderRepository = new OrderRepository();
        ShippingGateway shippingGateway = new ShippingGateway();

        public void cancelOrder(Long orderId) {
            Order order = orderRepository.findById(orderId);
            String shippingStatus = shippingGateway.getShippingStatus(orderId);
            order.cancel(shippingStatus);
        }
    }

    static class ShippingGateway {
        public String getShippingStatus(Long orderId) {
            return "shipped";
        }
    }

    static class OrderRepository {
        public Order findById(Long orderId) {
            return new Order(orderId, "new");
        }
    }

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    static class Order {
        @Id
        private Long id;

        private String status;

        void cancel(String shippingStatus) throws IllegalStateException {
            if ("shipped".equals(shippingStatus)) {
                throw new IllegalStateException("Can't cancel a shipped order.");
            }
            this.status = "cancelled";
        }
    }
}
