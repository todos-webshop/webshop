package webshop.product;

import java.text.Normalizer;
import java.util.Objects;

public class Product {

private long id;
private String code;
private String name;
private String address;
private String manufacturer;
private int price;

    public Product(long id, String code, String name, String manufacturer, int price) {
        this.id = id;
        this.code = code;
        this.name = name;
        address = generateAddress();
        this.manufacturer = manufacturer;
        this.price = price;
    }

    private String generateAddress(){
        String address = name.toLowerCase().replaceAll(" ", "_");
        return normalize(address);
    }

    private String normalize(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code.equals(product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
