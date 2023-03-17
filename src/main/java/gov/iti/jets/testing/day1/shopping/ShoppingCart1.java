package gov.iti.jets.testing.day1.shopping;

import java.util.*;

public class ShoppingCart1 {
    private List<LineItem1> lineItem1s = new ArrayList<>();

    public void addProduct( Product1 product ) {
        Optional<LineItem1> lineItemOptional = lineItem1s.stream()
                                                         .filter( l -> l.product.equals( product ) )
                                                         .findAny();

        if (lineItemOptional.isPresent()) {
            lineItemOptional.get().incrementCount();
        } else {
            LineItem1 newLineItem1 = new LineItem1();
            newLineItem1.product = product;
            newLineItem1.count = 1;
            lineItem1s.add(newLineItem1);
        }

//        productToCount.merge(product, 1, Integer::sum);
    }

    public int getCount( Product1 product ) {
        Optional<LineItem1> lineItemOptional = lineItem1s.stream()
                                                         .filter( l -> l.product.equals( product ) )
                                                         .findAny();

        return lineItemOptional
                .map( p -> p.count )
                .orElse( 0 );
    }

    // Breaks encapsulation - state verification
//    public Map<Product1, Integer> getProductToCount() {
//        return productToCount;
//    }

    // does it have to be Set? Can't it be a collection?
    // call contains on this method
//    public Set<Product1> getProducts() {
//        return productToCount.keySet();
//    }

//    public boolean containsProduct(Product1 p) {
//        return productToCount.containsKey(p);
//    }

//    public int calculateTotal() {
//        return productToCount.entrySet()
//                .stream()
//                .mapToInt( e -> e.getKey().price() * e.getValue() )
//                .sum();
//    }
}
