package webshop.basket;

import webshop.user.UserData;

import java.util.*;

public class Basket {

    private long id;
    private UserData basketOwner;
    private List<BasketItem> basketItems = new ArrayList<>();

    public Basket(long id, UserData basketOwner) {
        this.id = id;
        this.basketOwner = basketOwner;
    }

    public List<BasketItem> getBasketItems() {
        return new ArrayList<>(basketItems);
    }

    public void addBasketItem(BasketItem basketItem) {
        basketItems.add(basketItem);
    }

    public long getId() {
        return id;
    }

    public UserData getBasketOwner() {
        return basketOwner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return basketOwner.equals(basket.basketOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketOwner);
    }
}
