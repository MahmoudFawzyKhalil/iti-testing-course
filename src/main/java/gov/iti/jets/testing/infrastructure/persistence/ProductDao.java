package gov.iti.jets.testing.infrastructure.persistence;

import gov.iti.jets.testing.domain.Product;
import jakarta.persistence.EntityManager;

public class ProductDao {

    public static void save(Product product, EntityManager em) {
        em.persist(product);
    }
}
