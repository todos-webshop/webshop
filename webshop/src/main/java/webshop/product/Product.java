package webshop.product;

public class Product {

private long id;
private String code;
private String name;
private String address;
private String manufacturer;
private int price;



    public Product(long id, String code, String name, String address, String manufacturer, int price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address = address;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public long getId() {
        return id;
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
