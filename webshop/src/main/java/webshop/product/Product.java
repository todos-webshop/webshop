package webshop.product;

public class Product {

private long id;
private String code;
private String name;
private String address;
private String manufacturer;
private int price;



    public Product( String code, String name, String address, String manufacturer, int price) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.manufacturer = manufacturer;
        this.price = price;
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
