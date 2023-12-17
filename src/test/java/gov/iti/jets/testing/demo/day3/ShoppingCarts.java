package gov.iti.jets.testing.demo.day3;

import gov.iti.jets.testing.domain.Product;
import gov.iti.jets.testing.domain.ShoppingCart;

import java.util.List;

public class ShoppingCarts {

    public static ShoppingCart randomShoppingCart( Long userId, List<Product> products ) {
        ShoppingCart shoppingCart = ShoppingCart
                .builder()
                .userId( userId )
                .build();
        products.forEach( shoppingCart::addProduct );
        return shoppingCart;
    }
}
