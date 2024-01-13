package gov.iti.jets.testing.domain;


import jakarta.persistence.Embeddable;

@Embeddable
public class LineItem {

    private String productCode;

//    private int price;

    private int quantity;

    protected LineItem() {
    }

    public LineItem(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "LineItem{" +
               "productCode='" + productCode + '\'' +
               ", quantity=" + quantity +
               '}';
    }

    public void incrementQuantity() {
        quantity++;
    }
}
