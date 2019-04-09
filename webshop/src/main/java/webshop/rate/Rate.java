package webshop.rate;

import webshop.product.Product;
import webshop.user.User;

import java.time.LocalDate;

public class Rate {
    private long id;
    private String message;
    private int stars;
    private LocalDate date;
    private User user;
    private Product product;

    public Rate() {
    }

    public Rate(long id, String message, int stars, LocalDate date, User user, Product product) {
        this.id = id;
        this.message = message;
        this.stars = stars;
        this.date = changeNullDate(date);
        this.user = user;
        this.product = product;
    }

    public Rate( User user, Product product) {
        this.user = user;
        this.product = product;
    }

    private LocalDate changeNullDate(LocalDate date){
        if (date == null){
            return LocalDate.now();
        }
        return date;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getStars() {
        return stars;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
