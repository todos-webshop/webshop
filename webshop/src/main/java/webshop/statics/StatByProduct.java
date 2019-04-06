package webshop.statics;


public class StatByProduct {
    private int year;
    private int month;
    private String productName;
    private int productPrice;
    private int productCounter;
    private int amount;

    public StatByProduct(int year, int month, String productName, int productPrice, int productCounter, int amount) {
        this.year = year;
        this.month = month;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCounter = productCounter;
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductCounter() {
        return productCounter;
    }

    public int getAmount() {
        return amount;
    }
}
