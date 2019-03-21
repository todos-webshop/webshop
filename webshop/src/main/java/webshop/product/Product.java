package webshop.product;

import java.text.Normalizer;

public class Product {

private long id;
private String code;
private String name;
private String address;
private String manufacturer;
private int price;
private ProductStatus productStatus;

    public Product(long id, String code, String name, String manufacturer, int price) {
        this.id = id;
        this.code = code;
        this.name = name;
        address = generateAddress();
        this.manufacturer = manufacturer;
        this.price = price;
        this.productStatus = productStatus;
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

    public ProductStatus getProductStatus() {
        return productStatus;
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
}
