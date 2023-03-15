package gov.iti.jets.testing.demo.shopping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShoppingCart {

    // Make this public to test it using state verification
    // Refactor to use LineItem
    private Map<Product, Integer> productToCount = new HashMap<>();

    public void addProduct(Product product) {
        productToCount.merge(product, 1, Integer::sum);
    }

    // Breaks encapsulation - state verification
    public Map<Product, Integer> getProductToCount() {
        return productToCount;
    }

    // does it have to be Set? Can't it be a collection?
    // call contains on this method
    public Set<Product> getProducts() {
        return productToCount.keySet();
    }

//    public boolean containsProduct(Product p) {
//        return productToCount.containsKey(p);
//    }

    public int calculateTotal() {
        return productToCount.entrySet()
                             .stream()
                             .mapToInt(e -> e.getKey().price() * e.getValue())
                             .sum();
    }
}
