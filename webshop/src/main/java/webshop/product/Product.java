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
    private ProductStatus productStatus;

    public Product(long id, String code, String name, String manufacturer, int price,
                   ProductStatus productStatus) {
        this.id = id;
        this.code = code;
        this.name = deleteRedundantSpace(name.trim());;
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

    private String deleteRedundantSpace(String string){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length()-1;i++){
            if (string.charAt(i) == ' ' &&  string.charAt(i+1)==' '){

            }
            else {
                stringBuilder.append(string.charAt(i));
            }
        }
        if(string.equals("")){
            return "";
        }
        return stringBuilder.append(string.charAt(string.length()-1)).toString();
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

    public void setPrice(int price) {
        this.price = price;
    }
}