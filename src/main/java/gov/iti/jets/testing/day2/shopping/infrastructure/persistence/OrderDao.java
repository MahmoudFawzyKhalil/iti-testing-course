package gov.iti.jets.testing.day2.shopping.infrastructure.persistence;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import jakarta.persistence.EntityManager;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;

public class OrderDao {
    public static void save(Order order, EntityManager entityManager) {
        entityManager.persist(order);
    }
}
