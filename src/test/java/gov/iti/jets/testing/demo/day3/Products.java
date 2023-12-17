package gov.iti.jets.testing.demo.day3;

import com.github.javafaker.Faker;
import gov.iti.jets.testing.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Products {

    public static Product randomProduct() {
        return Product.builder()
                .code( randomCode() )
                .price( randomPrice() )
                .build();
    }

    private static int randomPrice() {
        return Faker.instance().number().numberBetween( 10_000, 50_000 );
    }

    private static String randomCode() {
        return Faker.instance().elderScrolls().firstName();
    }

    public static List<Product> randomProducts() {
        return new ArrayList<>(
                List.of(
                        randomProduct(),
                        randomProduct(),
                        randomProduct()
                )
        );
    }
}
