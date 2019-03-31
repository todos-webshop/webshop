package webshop.category;

import webshop.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private long id;
    private String categoryName;
    private int sequence;
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(long id, String categoryName, int sequence) {
        this.id = id;
        if (categoryName == null){
            this.categoryName = "No category";
        }
        else {
            this.categoryName = categoryName;
        }
        this.sequence = sequence;
    }

    public Category(long id, String categoryName, int sequence, List<Product> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.sequence = sequence;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getSequence() {
        return sequence;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
