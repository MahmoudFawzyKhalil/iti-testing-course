package gov.iti.jets.testing.demo.shopping;

import java.util.*;

public class ShoppingCart {
    private List<LineItem> lineItems = new ArrayList<>();

    public void addProduct( Product product ) {
        Optional<LineItem> lineItemOptional = lineItems.stream()
                .filter( l -> l.product.equals( product ) )
                .findAny();

        if (lineItemOptional.isPresent()) {
            lineItemOptional.get().incrementCount();
        } else {
            LineItem newLineItem = new LineItem();
            newLineItem.product = product;
            newLineItem.count = 1;
            lineItems.add( newLineItem );
        }

//        productToCount.merge(product, 1, Integer::sum);
    }

    public int getCount( Product product ) {
        Optional<LineItem> lineItemOptional = lineItems.stream()
                .filter( l -> l.product.equals( product ) )
                .findAny();

        return lineItemOptional
                .map( p -> p.count )
                .orElse( 0 );
    }

    // Breaks encapsulation - state verification
//    public Map<Product, Integer> getProductToCount() {
//        return productToCount;
//    }

    // does it have to be Set? Can't it be a collection?
    // call contains on this method
//    public Set<Product> getProducts() {
//        return productToCount.keySet();
//    }

//    public boolean containsProduct(Product p) {
//        return productToCount.containsKey(p);
//    }

//    public int calculateTotal() {
//        return productToCount.entrySet()
//                .stream()
//                .mapToInt( e -> e.getKey().price() * e.getValue() )
//                .sum();
//    }
}
