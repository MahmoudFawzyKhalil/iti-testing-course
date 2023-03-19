package gov.iti.jets.testing.demo.day3;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;

import java.util.List;

public class Orders {
    public static List<Order> findAll() {
        return Database.doInTransaction( em -> em
                .createQuery( "SELECT o FROM order o JOIN FETCH o.lineItems", Order.class )
                .getResultList() );
    }
}
