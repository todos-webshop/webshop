package webshop.rate;

import webshop.product.Product;
import webshop.user.User;

import java.time.LocalDate;

public class Rate {
    private long id;
    private String message;
    private int starts;
    private LocalDate date;
    private User user;
    private Product product;

    public Rate(long id, String message, int starts, LocalDate date, User user, Product product) {
        this.id = id;
        this.message = message;
        this.starts = starts;
        changeNullDate();
        this.user = user;
        this.product = product;
    }

    private void changeNullDate(){
        if (date == null){
            date = LocalDate.now();
        }
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getStarts() {
        return starts;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }
}
