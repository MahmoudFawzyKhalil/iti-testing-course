package gov.iti.jets.testing.domain;

import lombok.Builder;

import java.util.*;

// Lives in the HTTP session
public class ShoppingCart2 {
    private final Long userId;

//    private final Map<Product, Integer> productToQuantity;

    private final List<LineItem> lineItems = new ArrayList<>();

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    @Builder(toBuilder = true)
    public ShoppingCart2(Long userId) {
        this.userId = userId;
    }

    public void addProduct(Product product) {
        Optional<LineItem> lineItemByCode = lineItems
                .stream()
                .filter(i -> i.getProductCode().equals(product.getCode()))
                .findFirst();
        lineItemByCode.ifPresentOrElse(
                LineItem::incrementQuantity,
                () -> lineItems.add(new LineItem(product.getCode(), 1))
        );
    }
//
//    // Needs to be maintained
//    // Not a business requirement
//    // Expose a wider API - external clients will depend on anything you make public
//    public boolean contains(Product product) {
//        return productToQuantity.containsKey(product);
//    }
//
//    /*package private*/ Set<LineItem> createLineItems() {
//        return productToQuantity.entrySet()
//                .stream()
//                .map(e -> {
//                    String productCode = e.getKey().getCode();
//                    Integer quantity = e.getValue();
//                    return new LineItem(productCode, quantity);
//                })
//                .collect(Collectors.toUnmodifiableSet());
//    }
//
//    public int calculateTotal() {
//        return productToQuantity.entrySet()
//                .stream()
//                .mapToInt(e -> e.getKey().getPrice() * e.getValue())
//                .sum();
//    }
//
//    public Long getUserId() {
//        return this.userId;
//    }
//
//    public Integer getCount(Product product) {
//        return productToQuantity.get(product);
//    }
}
