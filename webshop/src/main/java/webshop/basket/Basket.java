package webshop.basket;

import webshop.product.Product;
import webshop.user.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Basket {

    private long id;
    private UserData basketOwner;
    private Map<Product, Integer> basketItems = new HashMap<>();

    public Basket(long id, UserData basketOwner) {
        this.id = id;
        this.basketOwner = basketOwner;
    }

    public Map<Product, Integer> getBasketItems() {
        return new HashMap<>(basketItems);
    }

    // only 1 allowed of each product for now:
    public void addProduct(Product product) {
        basketItems.put(product, 1);
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
