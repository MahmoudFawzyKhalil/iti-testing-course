package gov.iti.jets.testing.day2.shopping.infrastructure.persistence;

import gov.iti.jets.testing.day2.shopping.domain.Product;
import jakarta.persistence.EntityManager;

public class ProductDao {

    public static void save(Product product, EntityManager em) {
        em.persist(product);
    }
}
