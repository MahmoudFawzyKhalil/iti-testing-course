package gov.iti.jets.testing.infrastructure.persistence;

import gov.iti.jets.testing.domain.Order;
import jakarta.persistence.EntityManager;

public class OrderDao {
    public static void save(Order order, EntityManager entityManager) {
        entityManager.persist(order);
    }
}
