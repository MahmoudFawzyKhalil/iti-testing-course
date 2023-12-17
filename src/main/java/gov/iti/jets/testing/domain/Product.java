package gov.iti.jets.testing.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "products")
@Builder(toBuilder = true)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String code;

    @Getter
    private int price;

    protected Product() {
    }

    public Product(String code, int price) {
        this.code = code;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return Objects.equals(this.code, that.code) &&
                this.price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, price);
    }
}
