package gov.iti.jets.testing.day2.shopping.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Lives in the HTTP session
public class ShoppingCart {
    private final Long userId;

    private final Map<Product, Integer> productToQuantity = new HashMap<>();


    public ShoppingCart(Long userId) {
        this.userId = userId;
    }

    public void addProduct(Product product) {
        productToQuantity.merge(product, 1, Integer::sum);
    }

    /*package private*/ Set<LineItem> createLineItems() {
        return productToQuantity.entrySet()
                                .stream()
                                .map(e -> {
                                    String productCode = e.getKey().getCode();
                                    Integer quantity = e.getValue();
                                    return new LineItem(productCode, quantity);
                                })
                                .collect(Collectors.toUnmodifiableSet());
    }

    public int calculateTotal() {
        return productToQuantity.entrySet()
                                .stream()
                                .mapToInt(e -> e.getKey().getPrice() * e.getValue())
                                .sum();
    }

    public Long getUserId() {
        return this.userId;
    }
}
