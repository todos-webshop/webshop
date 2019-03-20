package webshop.product;

import java.text.Normalizer;

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
        return name.toLowerCase().replaceAll(" ", "_");
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
}
